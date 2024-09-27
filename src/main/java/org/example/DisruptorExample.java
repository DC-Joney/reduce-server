package org.wroker;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.turing.common.pool.thread.NamedThreadFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DisruptorExample {
    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();

        Condition condition = lock.newCondition();

        BlockEventHandler eventHandler = new BlockEventHandler(lock, condition);


        Disruptor<HandlerEvent> disruptor = new Disruptor<>(HandlerEvent::new,4,
                new NamedThreadFactory("test-"), ProducerType.SINGLE,new BlockingWaitStrategy());

        disruptor.handleEventsWith(eventHandler);

        ExecutorService executorService = Executors.newCachedThreadPool();

        disruptor.start();

        executorService.execute(()-> {

            for (int i = 0; i < 5; i++) {
                int finalI = i;
                disruptor.getRingBuffer().publishEvent((event, sequence, arg0) -> event.setCounter(finalI), finalI);
            }

        });



        TimeUnit.HOURS.sleep(1);


    }

    @RequiredArgsConstructor
    static class BlockEventHandler implements EventHandler<HandlerEvent> ,WorkHandler<HandlerEvent>{


        private final Lock lock;

        private final Condition condition;

        @Override
        public void onEvent(HandlerEvent event) throws Exception {
            lock.lock();
            try {
                log.info("event: {}" ,event.getCounter());
//                condition.await();
            }finally {
                lock.unlock();
            }
        }

        @Override
        public void onEvent(HandlerEvent event, long sequence, boolean endOfBatch) throws Exception {
            lock.lock();
            try {
                log.info("endOfBatch: {}, event:{}" ,endOfBatch,event.getCounter());
//                condition.await();
            }finally {
                lock.unlock();
            }

        }
    }




    @Setter
    @Getter
    static class HandlerEvent {

        int counter;

    }


}
