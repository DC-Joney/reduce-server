package org.reduce.server.raft.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import lombok.RequiredArgsConstructor;
import org.reduce.server.raft.RaftServer;
import org.raft.rpc.Response;
import org.raft.rpc.SearchKeyRequest;
import org.raft.rpc.SearchKeyResponse;

@RequiredArgsConstructor
public class SearchKeyProcessor extends AsyncUserProcessor<SearchKeyRequest> {

    private final RaftServer raftServer;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, SearchKeyRequest request) {
        raftServer.doRead(request)
                .getResponseFuture()
                .exceptionally(ex-> {
                    Response response = Response.newBuilder().setSuccess(false)
                            .setErrMsg(ex.getMessage()).build();

                    return SearchKeyResponse.newBuilder().setResponse(response).build();
                }).thenAccept(asyncCtx::sendResponse);
    }

    @Override
    public String interest() {
        return SearchKeyRequest.class.getName();
    }
}
