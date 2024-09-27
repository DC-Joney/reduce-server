package org.reduce.server.raft.processor;

import com.alipay.remoting.Connection;
import com.alipay.remoting.rpc.RpcServer;
import org.common.config.ClientConnectionPoolManager;
import org.common.raft.HotKeyRequest;
import org.common.raft.Response;
import org.common.rpc.KeyDistributionRequest;
import org.reduce.server.raft.handler.MessageProcessor;
import org.common.config.ConnectionPoolManager;
import org.reduce.server.raft.manager.KeyMetaManager;
import org.reduce.server.raft.manager.WorkerConnection;

public class KeyDistributionProcessor implements MessageProcessor<KeyDistributionRequest, Response> {

    private ClientConnectionPoolManager<WorkerConnection> connectionManager;

    private KeyMetaManager metaManager;

    private RpcServer rpcServer;

    @Override
    public Response onMessage(KeyDistributionRequest request) {
        HotKeyRequest hotKeyRequest = request.getMeta();
        String workerId = request.getWorkerId();
        //将其添加到元数据管理器与leader数据保持一致
        metaManager.addKeyRoute(hotKeyRequest.getKey(),workerId);
        //如果需要被计算的key在当前节点，那么将key下发到worker
        if (connectionManager.connectionKeys().contains(workerId)) {
            WorkerConnection workerConnection = connectionManager.findOneConnection(workerId);
            Connection connection = workerConnection.getConnection();
            rpcServer.oneway(connection, hotKeyRequest);
        }
        return null;
    }
}
