package org.reduce.server.raft;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.rpc.RpcServer;
import org.reduce.server.raft.handler.HotKeyMessageProcessor;
import org.reduce.server.raft.handler.SearchKeyMessageProcessor;
import org.reduce.server.raft.handler.TestWriteMessageProcessor;
import org.raft.processor.*;
import org.raft.rpc.*;
import org.reduce.server.raft.processor.*;
import org.reduce.server.raft.utiils.ProcessorRegistryCache;

public class RpcRequestProcessors {

    public static void addMessageProcessors(RaftServer raftServer, RpcServer rpcServer) {



        ProcessorRegistryCache.registerMessageType(AppMessage.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(HotKeyRequest.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(Response.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(HeartBeatRequest.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(HeartBeatResponse.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(SearchKeyRequest.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(SearchKeyResponse.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(TestWriteRequest.getDefaultInstance());

        rpcServer.registerUserProcessor(new HeartBeatProcessor());
        rpcServer.registerUserProcessor(new HotKeyProcessor(raftServer));
        rpcServer.registerUserProcessor(new AppProcessor(raftServer.getConnectionManager()));
        rpcServer.registerUserProcessor(new SearchKeyProcessor(raftServer));
        rpcServer.registerUserProcessor(new TestWriteProcessor(raftServer));

        //注册对应的处理器
        ProcessorRegistryCache.registerProcessor(HotKeyRequest.getDefaultInstance(), new HotKeyMessageProcessor());
        ProcessorRegistryCache.registerProcessor(SearchKeyRequest.getDefaultInstance(), new SearchKeyMessageProcessor());
        ProcessorRegistryCache.registerProcessor(TestWriteRequest.getDefaultInstance(), new TestWriteMessageProcessor());

        //标记當前請求為寫請求
        ProcessorRegistryCache.markWrite(HotKeyRequest.getDefaultInstance());
        ProcessorRegistryCache.markWrite(TestWriteRequest.getDefaultInstance());

        //标记为写请求
        ProcessorRegistryCache.markRead(SearchKeyRequest.getDefaultInstance());

        rpcServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, new ConnectionEventProcessor() {
            @Override
            public void onEvent(String remoteAddress, Connection connection) {
                System.out.println(connection.getRemoteAddress());
            }
        });

    }



}
