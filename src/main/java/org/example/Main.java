package org.wroker;

import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        final ByteBuf byteBuf = Unpooled.copiedBuffer("HelloWorld", Charset.defaultCharset());

        byteBuf.retain();

        ReferenceQueue<Object> queue = new ReferenceQueue<>();

        final ByteBufWrapper wrapper = new ByteBufWrapper(byteBuf, queue);

        System.gc();

        System.gc();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                byteBuf.release();
                byteBuf.release();

                System.out.println("release complete");

                wrapper.close();


                System.gc();


                System.gc();
            }
        }).start();

        for (;;) {
            ByteBufWrapper poll = (ByteBufWrapper) queue.poll();

            if (poll == null) {
                continue;
            }

            if (poll.closed) {
                System.out.println("111111");
            }
        }




    }

    static class ByteBufWrapper extends WeakReference<Object> {

        private boolean closed;

        public ByteBufWrapper(ByteBuf referent, ReferenceQueue<Object> q) {
            super(referent, q);
        }

        void close(){
            closed = true;
            clear();
        }

    }
}