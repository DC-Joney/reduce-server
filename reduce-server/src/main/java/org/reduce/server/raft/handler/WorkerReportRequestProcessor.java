package org.reduce.server.raft.handler;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import org.common.rpc.WorkerReportRequest;
import org.reduce.server.raft.manager.WorkerConnectionPoolManager;

public class WorkerReportRequestProcessor extends AsyncUserProcessor<WorkerReportRequest> {

    private WorkerConnectionPoolManager poolManager;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, WorkerReportRequest request) {
        String workerId = request.getWorkerId();

    }

    @Override
    public String interest() {
        return null;
    }
}
