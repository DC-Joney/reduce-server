package org.wroker;

import com.google.j2objc.annotations.Weak;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ReferenceDemo2 {

    public static void main(String[] args) throws InterruptedException {
        ByteBuf byteBuf = Unpooled.buffer();

        ReferenceQueue<Object> queue = new ReferenceQueue<>();

        ByteBufReference reference = new ByteBufReference(byteBuf,queue);


        reference.close();

        System.gc();
        System.gc();

        TimeUnit.SECONDS.sleep(2);

        Object poll = queue.poll();

        System.out.println(poll);


        int shift = (1 << 21) - 1;

        int i = ThreadLocalRandom.current().nextInt();


    }


    public static long sequenceRandom(){
        long currentTimeMillis = System.currentTimeMillis();
        int random = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);

        int i = (random >> 16) ^ random;

        return 0;

    }


    static class ByteBufReference extends WeakReference<Object> {

        public ByteBufReference(ByteBuf referent, ReferenceQueue<Object> q) {
            super(referent, q);
        }


        void close(){
            clear();
        }
    }
}
