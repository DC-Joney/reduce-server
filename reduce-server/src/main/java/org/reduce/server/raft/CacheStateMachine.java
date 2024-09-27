package org.reduce.server.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reduce.server.raft.handler.MessageProcessor;
import org.reduce.server.raft.utiils.ProcessorRegistryCache;
import org.reduce.server.raft.utiils.ProtoUtils;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class CacheStateMachine extends StateMachineAdapter {


    private final AtomicLong leaderTerm = new AtomicLong();

    @Override
    @SneakyThrows
    public void onApply(Iterator iter) {
        while (iter.hasNext()) {
            RaftClosure closure = null;
            Message message = null;
            log.info("StateMachine received message................................................................");

            if (iter.done() != null) {
                closure = (RaftClosure) iter.done();
                message = closure.getMessage();
            } else {
                ByteBuffer dataBuffer = iter.getData().duplicate();
                int flag = dataBuffer.getInt();
                Parser<Message> messageParser = ProcessorRegistryCache.getParser(flag);
                message = messageParser.parseFrom(dataBuffer.slice());
            }

            //如果closure == null 并且是读请求的话则直接忽略
            if (closure == null && ProtoUtils.isRead(message)) {
                iter.next();
                continue;
            }

            MessageProcessor<Message, Message> processor = ProcessorRegistryCache.getProcessor(message);
            Message response = processor.onMessage(message);
            if (closure != null) {
                closure.setResponse(response);
                closure.run(Status.OK().copy());
            }

            iter.next();
        }
    }


    @Override
    public void onLeaderStart(long term) {
        leaderTerm.set(term);
        super.onLeaderStart(term);
    }

    @Override
    public void onLeaderStop(Status status) {
        if (status.isOk()) {
            leaderTerm.set(-1);
        }

        log.info(status.getErrorMsg());
        super.onLeaderStop(status);
    }

    public boolean isLeader() {
        return leaderTerm.get() > 0;
    }

    @Override
    public boolean onSnapshotLoad(SnapshotReader reader) {
        return super.onSnapshotLoad(reader);
    }

    @Override
    public void onSnapshotSave(SnapshotWriter writer, Closure done) {
        super.onSnapshotSave(writer, done);
    }
}
