package org.wroker;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferTest {

    public static void main(String[] args) {
        ByteBuffer writeBuffer = ByteBuffer.allocateDirect(1024);

        writeBuffer.put("a".getBytes(StandardCharsets.UTF_8));

        ByteBuffer slice = writeBuffer.slice();


        slice.position(writeBuffer.position());
        slice.put("b".getBytes(StandardCharsets.UTF_8));


        System.out.println(slice);

    }
}
