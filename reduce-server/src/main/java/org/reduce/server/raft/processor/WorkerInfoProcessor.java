package org.reduce.server.raft.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import org.common.rpc.WorkerInfoRequest;
import org.reduce.server.raft.RaftServer;

public class WorkerInfoProcessor extends AsyncUserProcessor<WorkerInfoRequest> {


    private RaftServer raftServer;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, WorkerInfoRequest request) {
        //将信息上报给leader
        raftServer.doWrite(request)
                .getResponseFuture()
                .thenAccept(asyncCtx::sendResponse);
    }

    @Override
    public String interest() {
        return WorkerInfoRequest.class.getName();
    }
}
