package org.reduce.server.raft.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.google.protobuf.UInt64Value;
import org.raft.rpc.HeartBeatRequest;
import org.raft.rpc.HeartBeatResponse;

/**
 * 接受app发送的心跳
 */
public class HeartBeatProcessor extends AsyncUserProcessor<HeartBeatRequest> {

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, HeartBeatRequest request) {
        UInt64Value timestamp = request.getTimestamp();
        HeartBeatResponse response = HeartBeatResponse.newBuilder().setSuccess(true).build();
        asyncCtx.sendResponse(response);
    }

    @Override
    public String interest() {
        return HeartBeatRequest.class.getName();
    }
}
