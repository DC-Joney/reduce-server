package org.reduce.server.raft.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.google.protobuf.Message;
import lombok.RequiredArgsConstructor;
import org.common.raft.HotKeyRequest;
import org.common.raft.Response;
import org.common.rpc.KeyDistributionRequest;
import org.reduce.server.raft.RaftServer;
import org.reduce.server.raft.ResponseClosure;
import org.reduce.server.raft.manager.KeyMetaManager;

@RequiredArgsConstructor
public class HotKeyProcessor extends AsyncUserProcessor<HotKeyRequest> {

    private final RaftServer raftServer;

    private KeyMetaManager metaManager;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, HotKeyRequest request) {

        String appName = request.getAppName();
        String hotKey = request.getKey();

        Message writeRequest = request;

        //如果当前节点为leader节点
        if (raftServer.isLeader()) {
            //当前key被分配的workerId
            String workerId = metaManager.distributionKey(appName, hotKey);

            //将分配后的key对应的worker信息写入到raft server中
            writeRequest = KeyDistributionRequest.newBuilder()
                    .setWorkerId(workerId)
                    .setMeta(request).build();
        }


        //通过raft进行写入
        ResponseClosure closure = raftServer.doWrite(writeRequest);
        closure.getResponseFuture()
                .exceptionally(ex-> Response.newBuilder().setErrMsg(ex.getMessage())
                        .setSuccess(false).build())
                .thenAccept(asyncCtx::sendResponse);

    }

    @Override
    public String interest() {
        return HotKeyRequest.class.getName();
    }
}
