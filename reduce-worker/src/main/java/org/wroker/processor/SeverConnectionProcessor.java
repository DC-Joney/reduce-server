package org.wroker.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.Connection;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import org.common.rpc.ServerInfoRequest;
import org.wroker.manager.ServerConnectionManager;


@Deprecated
public class SeverConnectionProcessor extends AsyncUserProcessor<ServerInfoRequest> {

    ServerConnectionManager connectionManager;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ServerInfoRequest request) {
        Connection connection = bizCtx.getConnection();
        //获取当前server leader的 term，因为可能存在网络分区的情况
        connection.setAttribute("term", request.getLeaderTerm());
        connectionManager.addConnection(String.valueOf(request.getLeaderTerm()), connection);
    }

    @Override
    public String interest() {
        return ServerInfoRequest.class.getName();
    }
}
