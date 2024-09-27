package org.wroker.manager;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionManager;
import com.alipay.remoting.RemotingAddressParser;
import com.alipay.remoting.Url;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.sofa.jraft.JRaftUtils;
import com.alipay.sofa.jraft.util.timer.Timeout;
import com.alipay.sofa.jraft.util.timer.Timer;
import com.alipay.sofa.jraft.util.timer.TimerTask;
import com.google.protobuf.ProtocolStringList;
import com.netflix.config.DynamicContextualProperty;
import com.turing.common.notify.NotifyCenter;
import com.turing.common.notify.listener.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.common.Lifecycle;
import org.common.config.ConfigManager;
import org.common.config.ConnectionPoolManager;
import org.common.connection.ConnectionPool;
import org.common.rpc.NoticeLeaderConnectRequest;
import org.common.rpc.ServerConnectedFailRequest;
import org.common.rpc.ServerMetaResponse;
import org.wroker.event.ConnectionEvent;
import org.wroker.event.ServerAvailableEvent;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ServerConnectionManager implements Subscriber<ConnectionEvent>, ConnectionPoolManager, Lifecycle<Void> {

    /**
     * leader 对应的connection信息，当server出现分区时，需要将数据上报给所有leader
     */
    private final SortedMap<String, ConnectionPool> connectionMap = new ConcurrentSkipListMap<>(Comparator.reverseOrder());

    private static final Timer CONNECTION_POOL_SCAN_TIMER = JRaftUtils.raftTimerFactory().createTimer("");

    private final DynamicContextualProperty<List<String>> serversProperty = new DynamicContextualProperty<>("server.list", Collections.emptyList());

    private final ConnectionManager connectionManager;

    private final RpcServer rpcServer;

    private final FindAvailableServerTask serverTask;

    private final RemotingAddressParser addressParser;

    public ServerConnectionManager(RpcServer rpcServer) {
        this.connectionManager = rpcServer.getConnectionManager();
        this.addressParser = rpcServer.getAddressParser();
        this.rpcServer = rpcServer;
        this.serverTask = new FindAvailableServerTask();
    }

    @Override
    public void onEvent(ConnectionEvent connectionEvent) {
        if (connectionEvent.isClosed()) {
            remove(connectionEvent.getConnection());
        }
    }

    @Override
    public boolean init(Void opts) {
        CONNECTION_POOL_SCAN_TIMER.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                Iterator<Map.Entry<String, ConnectionPool>> iterator = connectionMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, ConnectionPool> entry = iterator.next();
                    ConnectionPool connectionPool = entry.getValue();
                    connectionPool.scan();
                    if (!connectionPool.hasConnection()) {
                        iterator.remove();
                    }
                }

                notifyServerChange();
                CONNECTION_POOL_SCAN_TIMER.newTimeout(this, 2000, TimeUnit.MILLISECONDS);
            }
        }, 5000, TimeUnit.MILLISECONDS);
        return true;
    }

    private void notifyServerChange() {
        if (connectionMap.isEmpty()) {
            //已经没有可用的server节点了，这个是在server节点变更的时候会遇到,需要进行等待直到leader节点可用
            NotifyCenter.publishEvent(new ServerAvailableEvent(ServerAvailableEvent.AvailableState.NOT_SERVERS));
            CONNECTION_POOL_SCAN_TIMER.newTimeout(serverTask, 5000, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void shutdown() {
        Iterator<Map.Entry<String, ConnectionPool>> iterator = connectionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ConnectionPool> entry = iterator.next();
            entry.getValue().removeAllAndTryClose();
            iterator.remove();
        }
    }

    @Override
    public Class<? extends ConnectionEvent> subscribeType() {
        return ConnectionEvent.class;
    }

    @Override
    public boolean remove(Connection connection) {
        String term = (String) connection.getAttribute("term");
        ConnectionPool serverConnection = connectionMap.get(term);
        if (serverConnection != null) {
            serverConnection.removeAndTryClose(connection);
            if (!serverConnection.hasConnection()) {
                connectionMap.remove(term);
                return true;
            }
        }

        return false;
    }

    @Override
    public void remove(String origin) {
        ConnectionPool removedPool = connectionMap.remove(origin);
        if (removedPool != null && removedPool.hasConnection()) {
            removedPool.removeAllAndTryClose();
        }
    }

    @Override
    public Connection findOneConnection(String origin) {
        return getConnection();
    }

    @Override
    public Set<String> connectionKeys() {
        return connectionMap.keySet();
    }

    public synchronized void addConnection(String term, Connection connection) {
        ConnectionPool serverConnection = connectionMap.get(term);
        if (serverConnection == null) {
            serverConnection = new ConnectionPool("", null);
            serverConnection.add(connection);
        }

        NotifyCenter.publishEvent(new ServerAvailableEvent(ServerAvailableEvent.AvailableState.ADD_SERVERS));
    }

    /**
     * 获取所有leader对应的connection连接
     */
    public List<Connection> getConnections() {
        List<Connection> connections = Lists.newArrayList();
        for (String term : connectionMap.keySet()) {
            ConnectionPool connectionPool = connectionMap.get(term);
            if (!connectionPool.hasConnection()) {
                continue;
            }

            Connection connection = connectionPool.get();
            connections.add(connection);
        }

        return connections;
    }

    /**
     * 获取可用的第一个leader的connection信息
     */
    public Connection getConnection() {
        String leaderTerm = connectionMap.firstKey();
        ConnectionPool connectionPool = connectionMap.get(leaderTerm);
        if (connectionPool == null || !connectionPool.hasConnection()) {
            for (String term : connectionMap.tailMap(leaderTerm).keySet()) {
                connectionPool = connectionMap.get(term);
                if (connectionPool.hasConnection()) {
                    break;
                }
            }
        }

        return connectionPool != null && connectionPool.hasConnection()
                ? connectionPool.get() : null;

    }

    @Override
    public boolean isEmpty() {
        return connectionMap.isEmpty();
    }

    private class FindAvailableServerTask implements TimerTask {

        private int index = 0;

        private Url leaderUrl;

        @Override
        public void run(Timeout timeout) throws Exception {

            //如果已经有leader来连接自己了，那么就不再去询问server节点
            if (!connectionMap.isEmpty()) {
                return;
            }

            Url serverUrl = leaderUrl;

            if (serverUrl == null) {
                serverUrl = switchNewServer();
            }

            if (serverUrl != null) {
                Connection connection = connectionManager.get(serverUrl.getUniqueKey());
                if (connection == null) {
                    serverUrl = switchNewServer();
                }
            }

            if (serverUrl == null) {
                log.error("No available servers, servers url is: {}", serversProperty.getValue());
                //5s之后从新请求
                CONNECTION_POOL_SCAN_TIMER.newTimeout(this, 5, TimeUnit.SECONDS);
                return;
            }

            Connection connection = connectionManager.get(serverUrl.getUniqueKey());

            ServerConnectedFailRequest failRequest = ServerConnectedFailRequest.newBuilder()
                    .addAllOldServers(serversProperty.getValue()).build();


            //获取所有的server节点信息
            ServerMetaResponse response = (ServerMetaResponse) rpcServer.invokeSync(connection, failRequest, 2000);
            //可能server节点正在选举，就进行等待
            if (response.getServersCount() == 0) {
                //5s之后从新请求
                CONNECTION_POOL_SCAN_TIMER.newTimeout(this, 5, TimeUnit.SECONDS);
            }

            ProtocolStringList serversList = response.getServersList();
            //将新的server list添加到配置中
            ConfigManager.setProperty("server.list", serversList);

            String leader = response.getLeader();
            Url newLeaderUrl = addressParser.parse(leader);

            //如果有新的leader，则创建对应的connection
            if (!newLeaderUrl.equals(leaderUrl)) {
                //将旧的leader的url删除，只维护新的leader一个连接即可
                connectionManager.remove(leaderUrl.getUniqueKey());
                this.leaderUrl = newLeaderUrl;
                leaderUrl.setConnNum(1);
                leaderUrl.setConnWarmup(true);
                connection = connectionManager.getAndCreateIfAbsent(leaderUrl);
            }


            NoticeLeaderConnectRequest connectRequest = NoticeLeaderConnectRequest.newBuilder().build();

            //如果这个时候还是没有leader来连接自己的话
            if (connectionMap.isEmpty()) {
                //通知leader节点来连接自己
                rpcServer.oneway(connection, connectRequest);
            }

        }

        /**
         * 切换到新的节点进行重试
         */
        private Url switchNewServer() {

            //删除对应的连接
            if (leaderUrl != null) {
                connectionManager.remove(leaderUrl.getUniqueKey());
            }

            List<String> urls = serversProperty.getValue();
            for (int i = 0; i < urls.size(); i++) {
                String url = urls.get(index++ % urls.size());
                try {
                    Url serverUrl = addressParser.parse(url);
                    Connection conn = connectionManager.get(serverUrl.getUniqueKey());
                    if (conn == null) {
                        serverUrl.setConnWarmup(true);
                        serverUrl.setConnNum(1);
                        Connection connection = connectionManager.getAndCreateIfAbsent(serverUrl);
                        if (connection != null) {
                            return serverUrl;
                        }

                        connectionManager.remove(serverUrl.getUniqueKey());
                    }

                } catch (Exception e) {
                    log.error("Cannot connect to " + url, e);
                }
            }

            return null;
        }
    }
}
