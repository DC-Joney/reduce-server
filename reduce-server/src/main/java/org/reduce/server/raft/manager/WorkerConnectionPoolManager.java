package org.reduce.server.raft.manager;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionManager;
import com.alipay.remoting.RemotingAddressParser;
import com.alipay.remoting.Url;
import com.alipay.remoting.rpc.RpcAddressParser;
import com.alipay.remoting.rpc.RpcClient;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicStringListProperty;
import com.turing.common.notify.NotifyCenter;
import com.turing.common.notify.listener.Subscriber;
import lombok.SneakyThrows;
import org.common.Lifecycle;
import org.common.config.ClientConnectionPoolManager;
import org.common.config.ConfigManager;
import org.common.connection.ConnectionEvent;
import org.common.connection.ConnectionPool;
import org.common.rpc.ServerState;
import org.common.rpc.ServerStateChangeRequest;
import org.common.rpc.WorkerReportRequest;
import org.reduce.server.raft.event.WorkerEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * worker节点的connection管理
 */
public class WorkerConnectionPoolManager implements ClientConnectionPoolManager<ConnectionPool>,
        Lifecycle<Void>,Runnable, Subscriber<ConnectionEvent> {

    private final Map<String, ConnectionPool> connectionMap = new ConcurrentHashMap<>();

    private final DynamicStringListProperty workerServersProperty = new DynamicStringListProperty("worker.list", Collections.emptyList());
    private final DynamicBooleanProperty leaderStateProperty = new DynamicBooleanProperty("leader.state",false);

    private static final DynamicIntProperty CONNECTION_COUNT;
    static {
        CONNECTION_COUNT = ConfigManager.getPropertyFactory().getIntProperty("server.connection.count", 6);
    }

    private static final String ORIGIN_PROPERTY = "origin";

    private ConnectionManager connectionManager;

    private final RemotingAddressParser addressParser = new RpcAddressParser();

    private RpcClient rpcClient = new RpcClient();

    private final Map<String, Integer> retryMap = new ConcurrentHashMap<>();

    @Override
    public boolean init(Void opts) {
        workerServersProperty.addCallback(this);
        leaderStateProperty.addCallback(this);
        rpcClient.startup();
        return false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    @SneakyThrows
    public void onEvent(ConnectionEvent event) {

        Connection connection = event.getConnection();

        if (event.isConnected()) {
            WorkerReportRequest request = (WorkerReportRequest) rpcClient.invokeSync(event.getConnection(), ServerStateChangeRequest.newBuilder()
                    .setState(ServerState.LEADER_START), 2000);

            connection.setAttribute("rpcClient", rpcClient);
            connection.setAttribute(ORIGIN_PROPERTY, request.getWorkerId());

            //添加connection到连接池
            addConnection(request.getWorkerId(), event.getConnection());
        }

        //从连接池中移除
        if (event.isClosed()) {
            remove(connection);

            //如果当前节点是leader节点
            if (leaderStateProperty.get()) {
                String workerId = (String) connection.getAttribute(ORIGIN_PROPERTY);
                Integer retryCount = retryMap.getOrDefault(workerId, 0);
                String remoteAddress = connection.getRemoteAddress().toString();
                Url workerUrl = rpcClient.getAddressParser().parse(remoteAddress);


                //
                int count = connectionManager.count(remoteAddress);

                if (count < 3 && retryCount < 3) {
                    connectionManager.createConnectionAndHealIfNeed(workerUrl);
                    retryMap.put(workerId, ++retryCount);
                    return;
                }

                if (count == 0) {
                    NotifyCenter.publishEvent(new WorkerEvent(workerId, WorkerEvent.WorkerState.DISCONNECT));
                }
            }


        }
    }

    @Override
    public Class<? extends ConnectionEvent> subscribeType() {
        return null;
    }

    @Override
    public void run() {

        if (leaderStateProperty.get()) {
            List<String> workerServers = workerServersProperty.get();
            for (String workerServer : workerServers) {
                Url workerUrl = addressParser.parse(workerServer);
                createNewSeverConnections(workerUrl);
            }

        }

        if (!leaderStateProperty.get()) {
            Set<String> poolKeys = connectionManager.getAll().keySet();
            poolKeys.forEach(poolKey -> connectionManager.remove(poolKey));
        }

    }


    private  void createNewSeverConnections(Url remoteUrl) {
        //重试的次数，如果连接池无法创建则自动跳转指针,重新尝试
        for (int i = 0; i < 3; i++) {
            try {
                remoteUrl.setConnWarmup(true);
                remoteUrl.setConnNum(CONNECTION_COUNT.get());
                //会自动补充connection到connection pool中
                //将当前连接池对应的url设置为实际的url连接池
            }catch (Exception e) {
                connectionManager.remove(remoteUrl.getUniqueKey());
            }
        }
    }

    @Override
    public void addConnection(String origin, Connection connection) {

    }

    @Override
    public boolean remove(Connection connection) {

    }

    @Override
    public void remove(String origin) {

    }

    @Override
    public Connection findOneConnection(String origin) {

    }

    @Override
    public ConnectionPool findConnection(String origin) {
        return null;
    }

    @Override
    public Set<String> connectionKeys() {
        return connectionMap.keySet();
    }
}
