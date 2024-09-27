package org.wroker.manager;

import com.alipay.remoting.*;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcServer;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicStringListProperty;
import com.turing.common.notify.NotifyCenter;
import com.turing.common.notify.listener.Subscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.common.config.ConfigManager;
import org.wroker.event.ConnectionEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequiredArgsConstructor
public class ConnectionPoolManager implements Subscriber<ConnectionEvent>,Runnable {

    private final RemotingAddressParser addressParser;

    private final ConnectionManager manager;

    //TODO: 待优化，将bolt的连接管理器改为自定义的连接池
    private ConnectionPool connectionPool;

    private  final AtomicLong roundIndex = new AtomicLong();

    /**
     * 当前连接池的指向的地址信息
     */
    private volatile Url remoteUrl;

    private static final AtomicBoolean START_STATE = new AtomicBoolean();

    private static final DynamicIntProperty CONNECTION_COUNT;
    static {
        CONNECTION_COUNT = ConfigManager.getPropertyFactory().getIntProperty("server.connection.count", 6);
    }

    private static final DynamicStringListProperty SERVER_LIST_PROPERTY;
    static {
        SERVER_LIST_PROPERTY = new DynamicStringListProperty("server.list", Collections.emptyList());
    }

    public ConnectionPoolManager(RpcServer rpcServer){
        this.manager = rpcServer.getConnectionManager();
        this.addressParser = rpcServer.getAddressParser();
        SERVER_LIST_PROPERTY.addCallback(this);
        NotifyCenter.registerSubscriber(this);
    }

    @Override
    public void onEvent(ConnectionEvent connectionEvent) {
        if (connectionEvent.isClosed()) {
            //连接池数量
            int connectionCount = CONNECTION_COUNT.get();
            //所有的server列表
            List<String> serverUrls = SERVER_LIST_PROPERTY.get();
            //触发关闭的server地址
            String remoteAddress = connectionEvent.getRemoteAddress();
            //如果该连接已经不存在了，server节点可能存在变更, 那么创建新的连接池
            if (!serverUrls.contains(remoteAddress)) {
                return;
            }

            Url serverUrl = addressParser.parse(remoteAddress);
            int count = manager.count(serverUrl.getUniqueKey());
            int threshold = Math.round(connectionCount * 0.2f);

            //如果连接池中的连接数等于阈值说明该节点可能故障了，则切换新的节点
            if (count <= threshold) {
                //创建新的server的连接池
                createNewSeverConnections(serverUrl, getNextIndex());
                //将该连接对应的连接池清除掉
                manager.remove(serverUrl.getUniqueKey());
            }

        }
    }

    /**
     * 获取connection连接，用于向server上报key的信息
     */
    public Connection getConnection(){
        List<String> reduceServers = SERVER_LIST_PROPERTY.getValue();
        String reduceServer = reduceServers.get(getIndex());
        Url serverUrl = addressParser.parse(reduceServer);
        return manager.get(serverUrl.getUniqueKey());
    }





    private synchronized void createNewSeverConnections(Url oldRemoteUrl, int newIndex) {

        //如果server地址已经改变了，说明已经更新过连接池了
        if (oldRemoteUrl != null && !remoteUrl.equals(oldRemoteUrl)) {
            return;
        }

        //重试的次数，如果连接池无法创建则自动跳转指针,重新尝试
        for (int i = 0; i < 3; i++) {
            List<String> serverUrls = SERVER_LIST_PROPERTY.get();
            Url newServerUrl  = addressParser.parse(serverUrls.get(newIndex));
            try {
                newServerUrl.setConnWarmup(true);
                newServerUrl.setConnNum(CONNECTION_COUNT.get());
                //会自动补充connection到connection pool中
                manager.createConnectionInManagement(newServerUrl);
                //将当前连接池对应的url设置为实际的url连接池
                this.remoteUrl = newServerUrl;
            }catch (Exception e) {
                newIndex = getNextIndex();
                manager.remove(newServerUrl.getUniqueKey());
            }
        }
    }


    int getIndex() {
        return (int) roundIndex.get() % SERVER_LIST_PROPERTY.get().size();
    }


    int getNextIndex() {
        return (int) roundIndex.incrementAndGet() % SERVER_LIST_PROPERTY.get().size();
    }

    @Override
    public void run() {
        int index = getIndex();
        //如果是第一次启动,则初始化url对应的连接池信息
        if (START_STATE.compareAndSet(false, true)) {
            //创建新的连接
            createNewSeverConnections(null, index);
            return;
        }

        List<String> serverUrls = SERVER_LIST_PROPERTY.get();
        //如果新的节点中不包含当前的节点，那么将当前的节点清除并且切换到下一个节点
        if (!serverUrls.contains(this.remoteUrl.getUniqueKey())) {
            createNewSeverConnections(remoteUrl, getNextIndex());
            //清除连接池
            manager.remove(remoteUrl.getUniqueKey());
        }

    }




    public static DynamicStringListProperty getServerProperty() {
        return SERVER_LIST_PROPERTY;
    }

    @Override
    public Class<? extends ConnectionEvent> subscribeType() {
        return ConnectionEvent.class;
    }
}
