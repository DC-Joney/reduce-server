package org.wroker;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.rpc.RpcServer;
import org.raft.manager.AppConnectionManager;
import org.raft.CloseConnectionEventProcessor;
import org.raft.processor.AppProcessor;
import org.raft.processor.TestWriteProcessor;
import org.raft.rpc.AppMessage;
import org.raft.rpc.Response;
import org.raft.rpc.TestWriteRequest;
import org.raft.utiils.ProcessorRegistryCache;

public class RpcServerExample {

    public static void main(String[] args) {

        ProcessorRegistryCache.registerMessageType(AppMessage.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(Response.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(TestWriteRequest.getDefaultInstance());



        RpcServer rpcServer = new RpcServer(8081);
        AppConnectionManager manager = new AppConnectionManager(rpcServer.getConnectionManager(), rpcServer);
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CLOSE,new CloseConnectionEventProcessor(manager));
        rpcServer.registerUserProcessor(new AppProcessor(manager));
        rpcServer.registerUserProcessor(new TestWriteProcessor(null));
        rpcServer.startup();
    }
}
