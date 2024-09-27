package org.reduce.server.raft.manager;


import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionManager;
import com.alipay.remoting.InvokeCallback;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@Slf4j
@RequiredArgsConstructor
public class AppConnectionManager {

    private final Map<String, List<Connection>> connectionMap = new ConcurrentHashMap<>();

    private final ConnectionManager manager;

    @Getter
    private final RpcServer rpcServer;

    public void addConnection(String appName, Connection connection){
        connectionMap.compute(appName,(app, connections) -> {
            if (connections == null) {
                connections = new ArrayList<>();
            }

            connections.add(connection);
            return connections;
        });

    }

    public List<Connection> getConnections(String appName) {
        return connectionMap.get(appName);
    }


    public void removeConnection(Connection connection) {
        String appName = (String) connection.getAttribute("APP_NAME");
        if (StringUtils.hasText(appName)) {
            List<Connection> connections;
            if ((connections = connectionMap.get(appName)) != null) {
                connections.remove(connection);
            }

        }
//        manager.remove(connection);
    }


    public CompletableFuture<Boolean> sendMessage(String appName, Object message) {

        CompletableFuture<Boolean> successFuture = new CompletableFuture<>();

        List<Connection> connections = getConnections(appName);
        for (Connection connection : connections) {
            try {
                rpcServer.oneway(connection, message);
            } catch (RemotingException e) {
                log.error("Call error",e);
                successFuture.complete(false);
                break;
            }
        }

        successFuture.complete(true);
        return successFuture;

    }
}
