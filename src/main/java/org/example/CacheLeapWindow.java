package org.wroker;

import com.turing.common.utils.SystemClock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 用于统计的滑动窗口实现，只统计数值
 *
 * @author zy
 */
public class CacheLeapWindow {

    /**
     * 开始时间
     */
    private final long startTime;

    /**
     * 每个窗口的间隔时间
     */
    private final int windowInterval;

    /**
     * 用于存储窗口数据
     */
    private final AtomicLongArray array;

    /**
     * mask 用于计算对应的窗口index
     */
    private final int mask;

    /**
     * 高21 bit用于存储count数量，低 41bit存储时间，最后1bit用于作为更新标识
     */
    private static final int BASE_SHIFT = 42;

    private static final long MAGIC = ~(-1L << BASE_SHIFT);

    /**
     * window窗口的总大小，必须为power2
     */
    private final int windowTime;

    public CacheLeapWindow(int windowInterval, int windowSize) {
        //为了避免第一个窗口的初始值为0与实际计算冲突，所有默认开始时间为 创建时间 - 一个窗口的间隔时间
        //保证第一个窗口的开始时间一定 >= windowInterval
        this.startTime = SystemClock.now() - windowInterval;
        this.windowInterval = windowInterval;
        this.array = new AtomicLongArray(power2(windowSize));
        this.mask = array.length() - 1;
        this.windowTime = windowInterval * array.length();
    }

    /**
     * 获取当前窗口的数值
     *
     * @param nowTime 当前时间
     */
    public long currentWindow(long nowTime) {
        //计算间隔时间
        long deltaTime = nowTime - startTime;
        //计算窗口index
        int index = (int) (deltaTime / windowInterval & mask);
        //计算窗口的开始时间
        long startTime = deltaTime - deltaTime % windowInterval;
        for (; ; ) {
            //获取对应的窗口
            long window = array.get(index);
            //如果window == 0表示还未初始化
            if (window == 0) {
                //通过cas进行初始化
                if (array.compareAndSet(index, 0, startTime << 1)) {
                    break;
                } else {
                    Thread.yield();
                }
            }
            //如果窗口的开始时间 == 实际计算出的开始时间则认为是同一个窗口
            else if ((window & MAGIC) >> 1 == startTime) {
                break;
            }
            //这里 < startTime 表示为窗口过期
            else if ((window & MAGIC) >> 1 < startTime) {
                //如果低1bit == 0表示当前正在更新，避免后面执行的cas浪费资源
                if ((window & 1) == 0 && array.compareAndSet(index, window, window | 1)) {
                    //将窗口的时间更新为最新时间
                    array.set(index, startTime << 1);
                    break;
                } else {
                    Thread.yield();
                }
            }
        }


        return array.get(index);

    }

    /**
     * 获取对应的窗口index
     *
     * @param nowTime 当前时间
     */
    public int getIndex(long nowTime) {
        long deltaTime = nowTime - startTime;
        return (int) (deltaTime / windowInterval & mask);
    }

    /**
     * 获取当前窗口的数量
     *
     * @param nowTime 当前时间
     */
    public int getCount(long nowTime) {
        return (int) (currentWindow(nowTime) >>> BASE_SHIFT);
    }

    /**
     * 获取总的数量统计
     */
    public int sumCount() {
        long count = 0;
        long nowTime = SystemClock.now();
        for (int i = 0; i < array.length(); i++) {
            long window = array.get(i);
            if (!isDep(window, nowTime)) {
                count += (window >>> BASE_SHIFT);
            }
        }

        return (int) count;

    }

    /**
     * 判断窗口是否过期
     *
     * @param window  具体的窗口值
     * @param nowTime 当前时间
     */
    public boolean isDep(long window, long nowTime) {
        long windowStartTime = (window & MAGIC) >> 1;
        long deltaTime = nowTime - windowInterval - startTime;
        return deltaTime - windowStartTime > windowTime;

    }

    /**
     * 添加count
     *
     * @param nowTime 当前时间
     * @return 返回当前窗口统计的count值
     */
    public int addCount(long nowTime, int count) {
        int index = getIndex(nowTime);
        for (; ; ) {
            long window = currentWindow(nowTime);

            //保留最后1bit，避免并发溢出
            if (Long.numberOfLeadingZeros(window) < 1) {
                break;
            }

            //更新count值
            if (array.compareAndSet(index, window, window + (count * (MAGIC + 1)))) {
                break;
            } else {
                Thread.yield();
            }

        }


        return (int) (currentWindow(nowTime) >>> BASE_SHIFT);
    }


    static int power2(int h) {
        return 1 << -(Integer.numberOfLeadingZeros(h - 1));
    }

    public static void main(String[] args) throws InterruptedException {

        CacheLeapWindow cacheLeapWindow = new CacheLeapWindow(1000, 4);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                for (int h = 0; h < 5; h++) {
                    cacheLeapWindow.addCount(SystemClock.now(), 1);
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        TimeUnit.MILLISECONDS.sleep(7200);
        System.out.println(cacheLeapWindow.sumCount());


    }

}
