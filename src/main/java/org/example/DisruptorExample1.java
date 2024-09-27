package org.wroker;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.turing.common.pool.thread.NamedThreadFactory;

import java.util.concurrent.TimeUnit;

public class DisruptorExample1 {

    public static void main(String[] args) {
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(OrderEvent::new,1024,
                new NamedThreadFactory("123"), ProducerType.SINGLE,new BlockingWaitStrategy());


        disruptor.handleEventsWithWorkerPool(event -> System.out.println(event.order), event -> {
            TimeUnit.SECONDS.sleep(10);
        });




        disruptor.start();

        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence) -> event.order = 1);
        ringBuffer.publishEvent((event, sequence) -> event.order = 2);
        ringBuffer.publishEvent((event, sequence) -> event.order = 3);
        ringBuffer.publishEvent((event, sequence) -> event.order = 4);
        ringBuffer.publishEvent((event, sequence) -> event.order = 5);
        ringBuffer.publishEvent((event, sequence) -> event.order = 6);


    }

    static class OrderEvent {

        int order;
    }
}
