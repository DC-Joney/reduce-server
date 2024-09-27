package org.reduce.server.raft.handler;

import org.raft.rpc.Response;
import org.raft.rpc.SearchKeyRequest;
import org.raft.rpc.SearchKeyResponse;

public class SearchKeyMessageProcessor implements MessageProcessor<SearchKeyRequest, SearchKeyResponse>{

    @Override
    public SearchKeyResponse onMessage(SearchKeyRequest request) {
        return SearchKeyResponse.newBuilder().setKey(request.getKey())
                .setAppName(request.getAppName())
                .setCount(10)
                .setResponse(Response.newBuilder().setSuccess(true).build())
                .build();
    }
}
