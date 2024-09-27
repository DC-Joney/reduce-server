package org.reduce.server.raft;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.turing.common.utils.SystemClock;
import org.raft.rpc.*;
import org.reduce.server.raft.utiils.ProcessorRegistryCache;

public class CacheStarterClient {


    public static void main(String[] args) throws RemotingException, InterruptedException {

        ProcessorRegistryCache.registerMessageType(AppMessage.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(Response.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(SearchKeyRequest.getDefaultInstance());
        ProcessorRegistryCache.registerMessageType(SearchKeyResponse.getDefaultInstance());
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

        for (int i = 0; i < 10; i++) {

            TestWriteRequest writeRequest = TestWriteRequest.newBuilder()
                    .setAppName("test")
                    .setValue("test" + i)
                    .build();

            rpcClient.oneway("localhost:8082", writeRequest);

        }

        TestWriteRequest writeRequest = TestWriteRequest.newBuilder()
                .setAppName("test")
                .setValue("end")
                .build();

        Response response = (Response) rpcClient.invokeSync("localhost:8082", writeRequest,200000);
        System.out.println(response.getData().toStringUtf8());

    }

}
