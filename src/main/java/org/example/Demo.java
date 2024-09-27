package org.wroker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Demo {

    static final long startMilli;

    static {
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        startMilli = startTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    static long sequenceRandom() {
        long currentTimeMillis = System.currentTimeMillis();
        int upperBound = (1 << 20) - 1;
        int lowerBound = 1 << 10;
        int random = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
        int lowerBits = (random >> 10) ^ random;
        long time = currentTimeMillis - startMilli;
        return time << 20 | lowerBits;
    }


    /**
     * 返回重新提交后的新的信息，低 3bit 为重试次数
     * @param version 版本号 5bit
     * @param magic 魔数  8bit
     * @param retryTimes 重试时间 5bit
     */
    static int recommit(int version, byte magic, int retryTimes){
        int upperBits = Integer.numberOfLeadingZeros(retryTimes << 1);
        return (upperBits << 16 | magic << 8 | version << 3) + 1;
    }


    public static void main(String[] args) {


        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


        executorService.scheduleWithFixedDelay(() -> {

            long l = sequenceRandom();
            System.out.println(l);
        }, 100,100, TimeUnit.MILLISECONDS);

    }
}
