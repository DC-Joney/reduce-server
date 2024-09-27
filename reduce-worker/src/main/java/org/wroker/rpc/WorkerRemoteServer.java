package org.wroker.rpc;

import com.alipay.remoting.*;
import com.alipay.remoting.rpc.RpcAddressParser;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.sofa.jraft.JRaftUtils;
import com.alipay.sofa.jraft.Lifecycle;
import com.alipay.sofa.jraft.util.timer.Timeout;
import com.alipay.sofa.jraft.util.timer.Timer;
import com.alipay.sofa.jraft.util.timer.TimerTask;
import com.turing.common.notify.NotifyCenter;
import com.turing.common.utils.SystemClock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.common.config.ConfigManager;
import org.common.config.ConnectionPoolManager;
import org.common.model.ReduceKeys;
import org.wroker.Constant;
import org.wroker.WorkerConfig;
import org.wroker.event.ConnectionEvent;
import org.wroker.manager.KeyRuleManager;
import org.wroker.manager.ServerConnectionManager;
import org.wroker.processor.KeyRuleProcessor;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WorkerRemoteServer implements Lifecycle<WorkerConfig> {

    private ConnectionManager manager;

    @Getter
    private RpcServer rpcServer;

    private RemotingAddressParser addressParser;

    /**
     * 用来存储所有的key的规则信息
     */
    private  KeyRuleManager ruleManager;

    private static final Timer REPORT_ALL_KEYS = JRaftUtils.raftTimerFactory().createTimer("report worker keys");


    @Getter
    private  String workerId;

    @Getter
    private  long startTime;

    private  ConnectionPoolManager poolManager;

    public WorkerRemoteServer() {
        this.addressParser = new RpcAddressParser();
    }

    @Override
    public boolean init(WorkerConfig opts) {
        this.rpcServer = new RpcServer();

        //初始化所有的监听器
        initConnectionListeners(rpcServer);
        //注册处理器
        rpcServer.registerUserProcessor(new KeyRuleProcessor());

        rpcServer.startup();
        this.manager = rpcServer.getConnectionManager();
        this.addressParser = rpcServer.getAddressParser();
        this.poolManager = new ServerConnectionManager(rpcServer);
        this.ruleManager = new KeyRuleManager(poolManager);
        this.workerId = opts.getWorkerId();
        this.startTime = SystemClock.now();

        //设置新的server 节点信息
        ConfigManager.setProperty(Constant.SERVER_PROPERTY_NAME, opts.getReduceServers());

        //启动manager节点
        ((ServerConnectionManager) this.poolManager).init(null);
        this.ruleManager.init(null);

        //每过一段时间进行一次上报
        REPORT_ALL_KEYS.newTimeout(new ReportAllKeysTask(), 1, TimeUnit.SECONDS);
        return true;
    }


    @RequiredArgsConstructor
    class ReportAllKeysTask implements TimerTask {

        @Override
        public void run(Timeout timeout) throws Exception {
            Set<String> allKeys = ruleManager.allKeys();
            Connection connection = poolManager.findOneConnection(null);
            //将数据上报给server端
            rpcServer.oneway(connection, new ReduceKeys());
            //等待下一次重新上报
            REPORT_ALL_KEYS.newTimeout(this, 10, TimeUnit.SECONDS);
        }
    }

    private void initConnectionListeners(RpcServer rpcServer) {
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, new ConnectionEventListener(ConnectionEventType.CONNECT));
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CONNECT_FAILED, new ConnectionEventListener(ConnectionEventType.CONNECT));
        rpcServer.addConnectionEventProcessor(ConnectionEventType.EXCEPTION, new ConnectionEventListener(ConnectionEventType.CONNECT));
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CLOSE, new ConnectionEventListener(ConnectionEventType.CONNECT));
    }

    @RequiredArgsConstructor
    static class ConnectionEventListener implements ConnectionEventProcessor {

        private final ConnectionEventType eventType;

        static {
            NotifyCenter.registerToPublisher(ConnectionEvent.class, 32);
        }

        @Override
        public void onEvent(String remoteAddress, Connection connection) {
            ConnectionEvent connectionEvent = ConnectionEvent.create(eventType, connection);
            NotifyCenter.publishEvent(connectionEvent);
        }
    }



    @Override
    public void shutdown() {
        rpcServer.shutdown();
    }
}
