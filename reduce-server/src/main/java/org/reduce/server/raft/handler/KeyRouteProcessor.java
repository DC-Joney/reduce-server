package org.reduce.server.raft.handler;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import org.common.raft.Response;
import org.common.rpc.KeyDistributionRequest;
import org.reduce.server.raft.event.HotKeyEvent;
import org.reduce.server.raft.handler.MessageProcessor;
import org.reduce.server.raft.manager.KeyMetaManager;

public class KeyRouteProcessor implements MessageProcessor<KeyDistributionRequest, Response> {

    private KeyMetaManager metaManager;

    private RingBuffer<HotKeyEvent> ringBuffer;

    @Override
    public Response onMessage(KeyDistributionRequest request) {
        String workerId = request.getWorkerId();
        String hotKey = request.getMeta().getKey();
        String appName = request.getMeta().getAppName();
        metaManager.addKeyRoute(hotKey, workerId);
        ringBuffer.publishEvent(new EventTranslator<HotKeyEvent>() {
            @Override
            public void translateTo(HotKeyEvent event, long sequence) {
                event.setWorkerId(workerId);
                event.setRequestMeta(request.getMeta());
            }
        });

        return Response.newBuilder()
                .setSuccess(true)
                .build();
    }
}
