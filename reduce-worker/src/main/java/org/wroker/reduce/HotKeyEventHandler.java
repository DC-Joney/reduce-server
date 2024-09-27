package org.wroker.reduce;

import cn.hutool.cache.impl.FIFOCache;
import com.alipay.remoting.Connection;
import com.alipay.remoting.RejectedExecutionPolicy;
import com.alipay.remoting.RejectionProcessableInvokeCallback;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.sofa.jraft.Lifecycle;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lmax.disruptor.EventHandler;
import com.netflix.config.DynamicIntProperty;
import com.turing.common.notify.listener.Subscriber;
import com.turing.common.pool.thread.ThreadPoolUtil;
import com.turing.common.pool.thread.policy.CallerRunOldPolicy;
import com.turing.common.utils.SystemClock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.common.config.ConnectionPoolManager;
import org.common.connection.ServiceThread;
import org.common.raft.HotKeyRequest;
import org.common.rpc.HotKeyReportRequest;
import org.wroker.event.ServerAvailableEvent;
import org.wroker.manager.ServerConnectionManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用于接受event事件
 */
@Slf4j
@RequiredArgsConstructor
public class HotKeyEventHandler implements EventHandler<HotKeyNoticeEvent>, Lifecycle<Void>, Subscriber<ServerAvailableEvent> {

    private final FIFOCache<String, HotKeyInfo> storeMap0 = new FIFOCache<>(65535);

    private final FIFOCache<String, HotKeyInfo> storeMap1 = new FIFOCache<>(65535);

    private final Cache<String, HotKeyInfo> retryCache = Caffeine.newBuilder()
            .maximumSize(65535)
            .softValues()
            .build();

    /**
     * 用于推送数据的map
     */
    private volatile FIFOCache<String, HotKeyInfo> pushMap;

    /**
     * 用于存储数据的map
     */
    private volatile FIFOCache<String, HotKeyInfo> storeMap;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 低10bit存储count， 其他bit暂留
     */
    private long value;

    /**
     * 用于管理 connection的连接
     */
    private ConnectionPoolManager connectionManager;


    private static final int COUNT_MAGIC = ~(-1 << 10);


    /**
     * 发送数据的阈值,满足count时，将数据发送到客户端
     */
    private final DynamicIntProperty thresholdProperty = new DynamicIntProperty("send.threshold", 200);

    /**
     * 发送的时间间隔, 时间单位为毫秒
     */
    private final DynamicIntProperty timeoutProperty = new DynamicIntProperty("send.timeout", 5000);


    /**
     * 如果server没有可用的节点，则已知等待直到有可用的节点
     */
    private volatile boolean serverAvailable = false;

    private final PushService pushService;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final ExecutorService sendExecutor;

    public HotKeyEventHandler(ConnectionPoolManager poolManager, ConnectionPoolManager manager) {
        this.connectionManager = poolManager;
        this.pushService = new PushService("push-key");
        this.sendExecutor = ThreadPoolUtil.newBuilder()
                .coreThreads(4)
                .maximumThreads(8)
                .keepAliveSeconds(60L)
                .poolName("push-executor")
                .rejectedHandler(new CallerRunOldPolicy())
                .workQueue(new ArrayBlockingQueue<>(2000))
                .build();
    }

    @Override
    public boolean init(Void opts) {
        this.pushService.start();
        this.pushMap = storeMap0;
        this.storeMap = storeMap1;
        this.startTime = SystemClock.now();
        return true;
    }

    @Override
    public void shutdown() {
        pushService.stop();
        storeMap0.clear();
        storeMap1.clear();
        retryCache.cleanUp();
        sendExecutor.shutdown();
    }

    @Override
    public void onEvent(ServerAvailableEvent event) {
        if (event.getState() == ServerAvailableEvent.AvailableState.NOT_SERVERS) {
            serverAvailable = false;
        }

        if (event.getState() == ServerAvailableEvent.AvailableState.ADD_SERVERS) {
            serverAvailable = true;
        }
    }

    @Override
    public Class<? extends ServerAvailableEvent> subscribeType() {
        return ServerAvailableEvent.class;
    }

    @Override
    public void onEvent(HotKeyNoticeEvent event, long sequence, boolean endOfBatch) throws Exception {
        HotKeyInfo hotKeyInfo = HotKeyInfo.from(event);

        try {
            //如果key的间隔时间小于间隔发送时间阈值，则直接发送
            if (event.getIntervalMs() < timeoutProperty.get()) {
                //将数据发送到客户端，如果发送失败直接放弃
                sendMessage(hotKeyInfo);
                return;
            }

            //自增count值
            value++;
            //将数据添加到storeMap
            addToStoreMap(hotKeyInfo);
            //如果上次的还没有发送完成，则直接返回等到所有的都发送完成后再切换
            if (!pushService.isWaiting()) {
                return;
            }

            //1、count >= 发送阈值
            if ((value & COUNT_MAGIC) >= thresholdProperty.get()) {
                //将value置为0
                value = 0;
                //如果为未运行状态，并且server节点是可用的，则唤醒当前线程
                if (pushService.isWaiting() && serverAvailable) {
                    pushService.wakeup();
                }
            }

        } catch (Exception e) {
            addToStoreMap(hotKeyInfo);
            value++;
        } finally {
            event.reset();
        }

    }

    private void addToStoreMap(HotKeyInfo hotKeyInfo) {
        //将这个key从retryCache中删除
        retryCache.invalidate(hotKeyInfo.cacheKey);
        //将该数据添加到storeMap中
        storeMap.put(hotKeyInfo.cacheKey, hotKeyInfo);
    }

    private void sendMessage(HotKeyInfo hotKeyInfo) {
        //如果消息已经过期了则不再上报, 因为该窗口内的数据已经过期了
        if (hotKeyInfo.isExpired() || !hotKeyInfo.needRetry()) {
            return;
        }

        ServerConnectionManager poolManager = (ServerConnectionManager) connectionManager;
        List<Connection> connections = poolManager.getConnections();
        for (Connection connection : connections) {
            RpcServer rpcServer = (RpcServer) connection.getAttribute("rpc");
            try {
                rpcServer.invokeWithCallback(connection, hotKeyInfo.request, RetryInvokeCallback.create(hotKeyInfo), 2000);
            } catch (Exception e) {
                addToRetryCache(hotKeyInfo);
            }
        }
    }

    private void addToRetryCache(HotKeyInfo hotKeyInfo) {
        readWriteLock.readLock().lock();
        try {
            retryCache.put(hotKeyInfo.cacheKey, hotKeyInfo);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    private void clearRetryCache() {
        readWriteLock.writeLock().lock();
        try {
            retryCache.invalidateAll();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }


    private void swapMap() {
        //清空pushMap中的数据
        pushMap = storeMap;
        if (storeMap == storeMap0)
            storeMap = storeMap1;
        else {
            storeMap = storeMap0;
        }
    }

    @RequiredArgsConstructor(staticName = "create")
    class RetryInvokeCallback implements RejectionProcessableInvokeCallback {

        private final HotKeyInfo hotKeyInfo;

        @Override
        public void onResponse(Object result) {
            log.info("onResponse received");
        }

        @Override
        public void onException(Throwable e) {
            addToRetryCache(hotKeyInfo);
        }

        @Override
        public Executor getExecutor() {
            return sendExecutor;
        }

        @Override
        public RejectedExecutionPolicy rejectedExecutionPolicy() {
            return RejectedExecutionPolicy.CALLER_HANDLE_EXCEPTION;
        }
    }

    class PushService extends ServiceThread {


        public PushService(String name) {
            super(name);
        }

        @Override
        protected void onWaitEnd() {
            pushMap.clear();
            swapMap();
        }

        @Override
        public void run() {
            while (isRunning()) {
                //如果没有节点可用则一直等待直到有可用的节点
                if (!serverAvailable) {
                    await(timeoutProperty.get(), TimeUnit.MILLISECONDS);
                }

                try {
                    Map<String, HotKeyInfo> sendMap = new HashMap<>(pushMap.size());
                    //将retryCache中的数据添加到sendMap，用于本次发送
                    readWriteLock.writeLock().lock();
                    try {
                        ConcurrentMap< String, HotKeyInfo> retryMap = retryCache.asMap();
                        sendMap.putAll(retryMap);
                        retryCache.invalidateAll();
                    }finally {
                        readWriteLock.writeLock().unlock();
                    }

                    //因为pushMap的数据时最新的，所以放在最后添加
                    Iterator<HotKeyInfo> iterator = pushMap.iterator();
                    while (iterator.hasNext()) {
                        HotKeyInfo hotKeyInfo = iterator.next();
                        sendMap.put(hotKeyInfo.cacheKey, hotKeyInfo);
                        iterator.remove();
                    }

                    if (!sendMap.isEmpty()) {
                        sendMap.forEach((k, hotKeyInfo) -> sendMessage(hotKeyInfo));
                    }

                } finally {
                    //将pushMap的数据清空
                    pushMap.clear();
                    //等待时间
                    await(timeoutProperty.get(), TimeUnit.MILLISECONDS);
                }


            }
        }
    }



    static class HotKeyInfo {

        private HotKeyReportRequest request;

        /**
         * 重试的次数
         */
        private final AtomicInteger retryCount = new AtomicInteger();

        /**
         * 缓存的key
         */
        private String cacheKey;


        boolean isExpired() {
            return request.getCurrentTime() + request.getIntervalMs() < SystemClock.now();
        }

        public static HotKeyInfo from(HotKeyNoticeEvent event) {
            HotKeyInfo info = new HotKeyInfo();
            info.request = HotKeyReportRequest.newBuilder()
                    .setKey(event.getHotKey())
                    .setAppName(event.getAppName())
                    .setCount(event.getCount())
                    .setCurrentTime(event.getCurrentTime())
                    .setIntervalMs(event.getIntervalMs())
                    .build();
            info.cacheKey = event.getAppName() + "@" + event.getHotKey();
            return info;
        }

        public boolean needRetry() {
            return retryCount.getAndIncrement() <= 5;
        }


    }
}
