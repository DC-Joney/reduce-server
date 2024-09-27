package org.wroker;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.FastThreadLocal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapTest {

    public static void main(String[] args) throws IOException {
        ConcurrentSkipListMap<Integer,Integer> test = new ConcurrentSkipListMap<Integer,Integer>();

        for (int i = 0; i < 100; i++) {
            test.put(i,i);
        }

        Thread thread;

        FastThreadLocal.removeAll();

        FileChannel open = FileChannel.open(Paths.get("a.txt"), StandardOpenOption.READ);


        ByteBuf buffer = Unpooled.buffer(1024);

        OutputStream outputStream = new ByteBufOutputStream(buffer);


        WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
        open.transferTo(0,open.size(), writableByteChannel);

        open.close();


        SelectorProvider provider = SelectorProvider.provider();

        System.out.println(provider);
    }
}
