package org.reduce.server.raft.handler;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.raft.rpc.Response;
import org.raft.rpc.TestWriteRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestWriteMessageProcessor implements MessageProcessor<TestWriteRequest, Response>{

    private List<String> writeStores = new ArrayList<>();

    @Override
    public Response onMessage(TestWriteRequest request) {
        log.info("TestWriteMessageProcessor................................................................");
        String value = request.getValue();
        writeStores.add(value);
        return Response.newBuilder()
                .setSuccess(true)
                .setData(ByteString.copyFromUtf8(writeStores.toString())).build();
    }
}
