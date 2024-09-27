package org.wroker;

import com.turing.common.utils.Power2;
import io.netty.util.concurrent.FastThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class RedisAllLock {


    private final RedissonClient redissonClient;

    /**
     * lock的名称
     */
    private final String lockKey;

    /**
     * lock的分段数量
     */
    private final int lockNumber;

    /**
     * 寻找分段锁的策略
     */
    private final LockStrategy lockStrategy;
    private LockStrategy fallbackStrategy;

    private List<String> lockKeys = new ArrayList<>();


    public RedisAllLock(RedissonClient redissonClient, String lockKey, int lockNumber, LockStrategy lockStrategy) {
        this.redissonClient = redissonClient;
        this.lockKey = lockKey;
        this.lockNumber = Power2.power2(lockNumber);
        this.lockStrategy = lockStrategy;
        for (int i = 0; i < lockNumber; i++) {
            lockKeys.add(lockKey + "" + i);
        }
    }

    private static final FastThreadLocal<String> lockKeyCache = new FastThreadLocal<>();

    public void lock() {
        String lockKey = lockStrategy.getLockKey(lockKeys);
        String firstLockKey = lockKey;
        do {
            RLock lock = redissonClient.getLock(lockKey);
            if (lock.tryLock()) {
                lockKeyCache.set(lockKey);
                break;
            }

            lockKey = lockStrategy.getLockKey(lockKeys);

        } while (!lockKey.equals(firstLockKey));

        //如果当前线程通过该方式依旧没有获取到锁，那么就不在继续获取，而是进入fallback策略
        if (lockKeyCache.get() == null) {
            lockKey = fallbackStrategy.getLockKey(lockKeys);

        }

        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
    }


    public void unlock() {
        String lockKey = lockKeyCache.get();
        if (StringUtils.isEmpty(lockKey)) {
            throw new IllegalStateException("Lock key is empty");
        }

        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }


    public interface LockStrategy {


        /**
         * 选择对应的lockKey
         */
        String getLockKey(List<String> lockKeys);

    }


    public static enum DefaultLockStrategy implements LockStrategy {

        /**
         * 随机算法
         */
        RANDOM() {
            @Override
            public String getLockKey(List<String> lockKeys) {
                return null;
            }
        },

        /**
         * 轮训策略
         */
        ROUND() {
            @Override
            public String getLockKey(List<String> lockKeys) {
                return null;
            }
        },

        /**
         * 最小加锁数
         */
        BEST_ALIVE() {

            private volatile Long[] stores;
            private AtomicBoolean initState = new AtomicBoolean(false);

            @Override
            public String getLockKey(List<String> lockKeys) {
                int lockSize = lockKeys.size();
                int slotCount = lockSize >> 3;
                for (; ; ) {
                    if (stores != null) {
                        break;
                    }

                    if (initState.compareAndSet(false, true)) {
                        //初始化bitMap，其中每8个bit用来存储当前锁被等待的请求数量，当数量达到256时，则不在增加
                        stores = new Long[slotCount];
                    }
                }

                //最小的minIndex，既寻找锁被请求最小的lockKey
                int minIndex = 0;
                //寻找最小的请求数量
                long minRequest = Long.MAX_VALUE;
                for (int i = 0; i < lockKeys.size(); i++) {
                    //寻找最小的slotIndex
                    int slotIndex = i >> 3;
                    //获取slot对应的值
                    long slotNumber = stores[slotIndex];
                    //获取对应的index值，既用于查找当前lockKey对应的bit位置
                    int index = i & ((1 << 3) - 1);
                    //计算起始位置
                    int startIndex = index << 3;
                    //计算结束位置
                    int endIndex = (index + 1) << 3;

                    //获取每个lockLey对应的请求数
                    //(0-7,8-15,16-23,23-31)
                    int requestNum = (int) (slotNumber >> startIndex & ((1 << endIndex) - 1));

                    //获取最小的请求对应的lockLey
                    if (requestNum < minRequest) {
                        minRequest = requestNum;
                        minIndex = i;
                    }

                }

                //获取对应的lockKey
                String lockKey = lockKeys.get(minIndex);
                int slotIndex = minIndex >> 3;
                int index = slotIndex & ((1 << 3) - 1);

                //如果请求数小于256，则+1
                if (minRequest < (1 << 8)) {
                    minRequest = minRequest + index == 0 ? 1 : 1L << (index << 3);
                    //将计算后的数据从新存入slot
                    stores[slotIndex] = minRequest;
                }

                return lockKey;
            }
        };


    }


}
