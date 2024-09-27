package org.reduce.server.raft.manager;

import com.alipay.remoting.Connection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorkerConnection {

    /**
     * 存储了worker所有的连接信息
     */
    private List<Connection> workerConnections = new CopyOnWriteArrayList<>();


    public void addConnection(Connection connection) {
        workerConnections.add(connection);
    }

    public Connection getConnection(){
        return null;
    }

    public void removeConnection(Connection connection) {
        workerConnections.remove(connection);
    }

    public boolean hasConnection() {
        return !workerConnections.isEmpty();
    }


}
