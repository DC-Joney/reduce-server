package org.reduce.server.raft.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reduce.server.raft.RaftServer;
import org.raft.rpc.Response;
import org.raft.rpc.TestWriteRequest;

@Slf4j
@RequiredArgsConstructor
public class TestWriteProcessor extends AsyncUserProcessor<TestWriteRequest> {

    private final RaftServer raftServer;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, TestWriteRequest request) {
        log.info("test write request........");
        raftServer.doWrite(request)
                .getResponseFuture()
                .exceptionally(ex-> Response.newBuilder().setSuccess(false)
                        .setErrMsg(ex.getMessage()).build())
                .thenAccept(asyncCtx::sendResponse);
    }

    @Override
    public String interest() {
        return TestWriteRequest.class.getName();
    }
}
