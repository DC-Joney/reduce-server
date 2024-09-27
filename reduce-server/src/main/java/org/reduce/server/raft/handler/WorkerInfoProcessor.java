package org.reduce.server.raft.handler;

import org.common.raft.Response;
import org.common.rpc.WorkerInfoRequest;
import org.common.rpc.WorkerReportRequest;
import org.reduce.server.raft.manager.KeyMetaManager;

@Deprecated
public class WorkerInfoProcessor implements MessageProcessor<WorkerInfoRequest, Response>{

    private KeyMetaManager manager;

    @Override
    public Response onMessage(WorkerInfoRequest request) {
        manager.addWorker(request.getWorkerMeta().getWorkerId(), request.getRaftPeer());
        return Response.newBuilder()
                .setSuccess(true)
                .build();
    }
}
