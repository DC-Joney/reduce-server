package org.reduce.server.raft.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import org.common.rpc.WorkerInfoRequest;
import org.common.rpc.WorkerReportRequest;
import org.reduce.server.raft.RaftServer;
import org.common.config.ConnectionPoolManager;
import org.reduce.server.raft.manager.WorkerConnection;

public class WorkerReportProcessor extends AsyncUserProcessor<WorkerReportRequest> {

    private ConnectionPoolManager<WorkerConnection> connectionManager;

    private RaftServer raftServer;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, WorkerReportRequest request) {
        String workerId = request.getWorkerId();
        connectionManager.addConnection(workerId, bizCtx.getConnection());

        WorkerInfoRequest infoRequest = WorkerInfoRequest.newBuilder()
                .setRaftPeer(raftServer.getSelfPeer().toString())
                .setWorkerMeta(request)
                .build();

        //将信息上报给leader
        raftServer.doWrite(infoRequest)
                .getResponseFuture()
                .thenAccept(asyncCtx::sendResponse);
    }

    @Override
    public String interest() {
        return null;
    }
}
