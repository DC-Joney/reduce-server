package org.wroker;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import org.redisson.api.RBitSet;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

public class RedissionDemo {

    public static void main(String[] args) {
//        RedissonClient redissonClient;
//
//        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter("");
//
//        RBitSet bitSet = redissonClient.getBitSet("");
//
//        RedisTemplate<String,String> redisTemplate;
//
//        BloomFilter<String> filter = BloomFilter.create(Funnels.integerFunnel(),3L);

        long maxValue = Long.MAX_VALUE;



        System.out.println(maxValue);
        System.out.println(1L << 63);

        System.out.println(optimalNumOfBits(1_0000_0000L, 0.02) / 8 /1024/1024);
    }


    private static long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }
}
