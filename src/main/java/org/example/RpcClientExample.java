package org.wroker;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.turing.common.utils.SystemClock;
import org.raft.rpc.*;
import org.raft.utiils.ProcessorRegistryCache;

public class RpcClientExample {


    public static void main(String[] args) throws RemotingException, InterruptedException {

        ProcessorRegistryCache.registerMessageType(AppMessage.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(Response.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(TestWriteRequest.getDefaultInstance());

        RpcClient rpcClient = new RpcClient();
        rpcClient.startup();
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CONNECT, new ConnectionEventProcessor() {
            @Override
            public void onEvent(String remoteAddress, Connection connection) {
                AppMessage appMessage = AppMessage.newBuilder().setAppName("test")
                        .setStartTime(SystemClock.now())
                        .build();
                try {
                    Object o = rpcClient.invokeSync(connection, appMessage, 2000);
                    System.out.println(o.getClass());
                } catch (RemotingException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        TestWriteRequest writeRequest = TestWriteRequest.newBuilder()
                .setAppName("test")
                .setValue("end")
                .build();
        try {
            Object o = rpcClient.invokeSync("localhost:8081", writeRequest, 2000);
            System.out.println(o.getClass());
        } catch (RemotingException | InterruptedException e) {
            throw new RuntimeException(e);
        }



    }

}
