package org.reduce.server.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.google.protobuf.Message;
import lombok.Setter;
import org.raft.rpc.Response;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Setter
public class ResponseClosure implements Closure {

    private final CompletableFuture<Message> responseFuture = new CompletableFuture<>();

    private Throwable throwable;

    private Message response;


    @Override
    public void run(Status status) {
        if (status.isOk()) {
            responseFuture.complete(response);
            return;
        }

        responseFuture.completeExceptionally(Objects.nonNull(throwable) ? new RaftStoreException(throwable.toString())
                : new RaftStoreException("operation failure"));
    }


    public CompletableFuture<Message> getResponseFuture() {
        return responseFuture;
    }
}
