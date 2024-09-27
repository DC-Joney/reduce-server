package org.reduce.server.raft.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.Connection;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reduce.server.raft.manager.AppConnectionManager;
import org.raft.rpc.AppMessage;
import org.raft.rpc.Response;

/**
 * 用于接受app的注册信息
 */
@Slf4j
@RequiredArgsConstructor
public class AppProcessor extends AsyncUserProcessor<AppMessage> {

    private final AppConnectionManager manager;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, AppMessage request) {
        log.info("客户端的新的连接................................");
        Connection connection = bizCtx.getConnection();
        connection.setAttribute("APP_NAME", request.getAppName());
        manager.addConnection(request.getAppName(), connection);
        asyncCtx.sendResponse(Response.newBuilder().setSuccess(true).build());
    }

    @Override
    public String interest() {
        return AppMessage.class.getName();
    }
}
