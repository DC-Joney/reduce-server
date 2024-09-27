package org.wroker;

import com.turing.common.cache.caffeine.CaffeineCache;
import org.checkerframework.checker.units.qual.K;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zy
 *
 * Redis 分段锁统计次数，用于查找在锁上等待次数最少得锁分段
 */
public class LockMetric {


    private final String[] lockKeys;

    private final long[] slots;

    private final RedissonClient redissonClient;


    public LockMetric(RedissonClient redissonClient, String[] lockKeys) {
        this.lockKeys = lockKeys;
        this.redissonClient = redissonClient;

        RBucket<Long[]> bucket = redissonClient.getBucket("");

        Assert.notEmpty(lockKeys, "Lock keys must not be empty");
        this.slots = new long[(lockKeys.length >> 3) + 1];
    }

    public String getLockKey() {



        //最小的minIndex，既寻找锁被请求最小的lockKey
        int minIndex = 0;
        //寻找最小的请求数量
        long minRequest = Long.MAX_VALUE;
        for (int i = 0; i < lockKeys.length; i++) {
            //寻找最小的slotIndex
            int slotIndex = i >> 3;
            //获取slot对应的值
            long slotNumber = slots[slotIndex];
            //获取对应的index值，既用于查找当前lockKey对应的bit位置
            int index = i & ((1 << 3) - 1);
            //计算起始位置
            int startIndex = index << 3;
            //计算结束位置
//            int endIndex = (index + 1) << 3;
            //获取每个lockLey对应的请求数
            //(0-7,8-15,16-23,23-31)
            int requestNum = (int) (slotNumber >> startIndex & ((1 << 3) - 1));
            //获取最小的请求对应的lockLey
            if (requestNum < minRequest) {
                minRequest = requestNum;
                minIndex = i;
            }
        }

        //获取对应的lockKey
        String lockKey = lockKeys[minIndex];
        int slotIndex = minIndex >> 3;
        int index = minIndex & ((1 << 3) - 1);

        //如果请求数小于256，则+1
        if (minRequest < (1 << 8)) {
            minRequest = slots[slotIndex] + (index == 0 ? 1 : 1L << (index << 3));
            //将计算后的数据从新存入slot
            slots[slotIndex] = minRequest;
        }

        return lockKey;
    }

    public static void main1(String[] args) {

        List<String> lockKeys = io.vavr.collection.List.of("test1","test2","test3","test4","test5","test6","test7","test8","test9","test10").asJava();

//        LockMetric strategy = new LockMetric(lockKeys.toArray(new String[]{}));

//        for (int i = 0; i < 55; i++) {
//            System.out.println(strategy.getLockKey());
//        }


        int x = Integer.numberOfLeadingZeros(16) | (1 << (16 -1));

        int shift = x << 16;

        int num = shift + 2;

        System.out.println(num >>> 16);
        System.out.println(Integer.toBinaryString(num));
        System.out.println(x << 16);
        System.out.println(Integer.toBinaryString(x));


        int hash = "test".hashCode(); // index = 2


        System.out.println(hash & 15);
        System.out.println(hash & 16);
        System.out.println(hash & 31);


        System.out.println(Integer.hashCode(4));

        //假设level = 3，maxLevel = 6
        for (int i = 1; i <= 3; ++i){
            System.out.println(i);
            System.out.println(i);

        }


        LinkedHashMap<String,String> hashMap = new LinkedHashMap<String,String>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 3;
            }
        };

        hashMap.put("test", "test");
        hashMap.put("test1", "test");
        hashMap.put("test2", "test");
        hashMap.put("test3", "test");
        hashMap.put("test4", "test");


        String test2 = hashMap.get("test2");
        String test4 = hashMap.get("test4");


        hashMap.forEach((k,v)-> System.out.println(k));






    }

    public static void main(String[] args) {
        int tableSize = 16;
        int newTableSize = 32;

        System.out.println(Integer.numberOfLeadingZeros(tableSize));
        //0000000000010000
        System.out.println(Integer.toBinaryString(tableSize));
        System.out.println(Integer.numberOfLeadingZeros(newTableSize));
        System.out.println(Integer.toBinaryString(newTableSize));

        int oldTableStamp = 1 << (16 -1) | 12;
        int newTableStamp = 1 << (16 -1) | 13;

        System.out.println(Integer.toBinaryString(oldTableStamp));
        System.out.println(Integer.toBinaryString(newTableStamp));
        System.out.println( oldTableStamp << 16);
    }


    static final int resizeStamp(int tableSize) {
        return Integer.numberOfLeadingZeros(tableSize) | (1 << (16 - 1));
    }




}
