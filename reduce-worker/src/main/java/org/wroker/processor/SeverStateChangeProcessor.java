package org.wroker.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.Connection;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.netflix.config.DynamicStringProperty;
import lombok.RequiredArgsConstructor;
import org.common.config.ConfigManager;
import org.common.rpc.ServerInfoRequest;
import org.common.rpc.ServerState;
import org.common.rpc.ServerStateChangeRequest;
import org.common.rpc.WorkerReportRequest;
import org.wroker.manager.ServerConnectionManager;
import org.wroker.rpc.WorkerRemoteServer;

@RequiredArgsConstructor
public class SeverStateChangeProcessor extends AsyncUserProcessor<ServerStateChangeRequest> {

    private final ServerConnectionManager connectionManager;

    private final WorkerRemoteServer remoteServer;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ServerStateChangeRequest request) {
        //当leader节点发生迁移时，移除对应的节点信息
        if (request.getState() == ServerState.LEADER_STOP) {
            //将当前term下所有的节点信息都删除
            connectionManager.remove(String.valueOf(request.getTerm()));
        }

        if (request.getState() == ServerState.LEADER_START) {
            Connection connection = bizCtx.getConnection();
            //获取当前server leader的 term，因为可能存在网络分区的情况
            connection.setAttribute("term", request.getTerm());
            connection.setAttribute("rpc", remoteServer.getRpcServer());
            connectionManager.addConnection(String.valueOf(request.getTerm()), connection);
            //将当前worker的信息告诉给server端
            asyncCtx.sendResponse(WorkerReportRequest.newBuilder()
                    .setWorkerId(remoteServer.getWorkerId())
                    .setStartTime(remoteServer.getStartTime())
                    .build());
        }


    }



    @Override
    public String interest() {
        return ServerInfoRequest.class.getName();
    }
}
