//package org.example;
//
//import com.github.benmanes.caffeine.cache.RemovalCause;
//import com.turing.common.utils.SystemClock;
//import lombok.RequiredArgsConstructor;
//import org.checkerframework.checker.units.qual.K;
//
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
//
//import static com.google.common.math.LongMath.ceilingPowerOfTwo;
//
///**
// * 并发时间轮实现
// *
// * @author zy
// */
//public class ConcurrentTimerWheel {
//
//
//    /**
//     * 时间轮的启动时间
//     */
//    private volatile long startTime;
//
//    static final int[] BUCKETS = { 64, 64, 32, 4, 1 };
//
//    private static final long[] UNITS = new long[]{
//            ceilingPowerOfTwo(TimeUnit.SECONDS.toNanos(1)), // 1.07s
//            ceilingPowerOfTwo(TimeUnit.MINUTES.toNanos(1)), // 1.14m
//            ceilingPowerOfTwo(TimeUnit.HOURS.toNanos(1)),   // 1.22h
//            ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1)),    // 1.63d
//            BUCKETS[3] * ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1)), // 6.5d
//            BUCKETS[3] * ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1)), // 6.5d
//    };
//
//    static final long[] SHIFT = {
//            Long.numberOfTrailingZeros(UNITS[0]),
//            Long.numberOfTrailingZeros(UNITS[1]),
//            Long.numberOfTrailingZeros(UNITS[2]),
//            Long.numberOfTrailingZeros(UNITS[3]),
//            Long.numberOfTrailingZeros(UNITS[4]),
//    };
//
//    private final NodeManager[][] wheel;
//
//
//    public ConcurrentTimerWheel(long startTime) {
//        this.startTime = SystemClock.now();
//
//        wheel = new NodeManager[BUCKETS.length][1];
//        for (int i = 0; i < wheel.length; i++) {
//            wheel[i] = new NodeManager[BUCKETS[i]];
//            Arrays.fill(wheel[i], new NodeManager());
//        }
//    }
//
//
//    /**
//     * 查找延迟时间对应的wheel bucket
//     * @param delayTimeMs 延迟时间
//     */
//    NodeManager findBucket(long delayTimeMs) {
//        long duration = delayTimeMs - startTime;
//        int length = wheel.length - 1;
//        for (int i = 0; i < length; i++) {
//            if (duration < UNITS[i + 1]) {
//                long ticks = (delayTimeMs >>> SHIFT[i]);
//                int index = (int) (ticks & (wheel[i].length - 1));
//                return wheel[i][index];
//            }
//        }
//        return wheel[length][0];
//    }
//
//    /**
//     * 添加延迟任务
//     *
//     * @param taskName 任务的名称
//     * @param delayMs  延迟的时间
//     */
//    public void addTask(String taskName, long delayMs) {
//        long delayTime = delayMs - startTime;
//        NodeManager manager = findBucket(delayTime);
//        Node newNode = new Node(taskName, delayTime);
//        for (;;) {
//            Node oldTail = manager.tail;
//            if (manager.casTail(oldTail, newNode)) {
//                break;
//            }
//        }
//    }
//
//    /**
//     * 时间轮滑动
//     * @param currentTimeNanos 检测失效的时间
//     */
//    public void advance(long currentTimeNanos) {
//        long previousTimeNanos = startTime;
//        try {
//            startTime = currentTimeNanos;
//
//            // If wrapping, temporarily shift the clock for a positive comparison
//            if ((previousTimeNanos < 0) && (currentTimeNanos > 0)) {
//                previousTimeNanos += Long.MAX_VALUE;
//                currentTimeNanos += Long.MAX_VALUE;
//            }
//
//            //计算出所有轮次中过期的轮次
//            for (int i = 0; i < SHIFT.length; i++) {
//                long previousTicks = (previousTimeNanos >>> SHIFT[i]);
//                long currentTicks = (currentTimeNanos >>> SHIFT[i]);
//                if ((currentTicks - previousTicks) <= 0L) {
//                    break;
//                }
//                expire(i, previousTicks, currentTicks);
//            }
//        } catch (Throwable t) {
//            startTime = previousTimeNanos;
//            throw t;
//        }
//    }
//
//    void expire(int index, long previousTicks, long currentTicks) {
//       NodeManager[] timerWheel = wheel[index];
//        int mask = timerWheel.length - 1;
//
//        int steps = Math.min(1 + Math.abs((int) (currentTicks - previousTicks)), timerWheel.length);
//        int start = (int) (previousTicks & mask);
//        int end = start + steps;
//
//        for (int i = start; i < end; i++) {
//            NodeManager manager = timerWheel[i & mask];
//            Node head = manager.head;
//
//            com.github.benmanes.caffeine.cache.Node<K, V> prev = sentinel.getPreviousInVariableOrder();
//            com.github.benmanes.caffeine.cache.Node<K, V> node = sentinel.getNextInVariableOrder();
//            sentinel.setPreviousInVariableOrder(sentinel);
//            sentinel.setNextInVariableOrder(sentinel);
//
//            while (node != sentinel) {
//                com.github.benmanes.caffeine.cache.Node<K, V> next = node.getNextInVariableOrder();
//                node.setPreviousInVariableOrder(null);
//                node.setNextInVariableOrder(null);
//
//                try {
//                    if (((node.getVariableTime() - nanos) > 0)
//                            || !cache.evictEntry(node, RemovalCause.EXPIRED, nanos)) {
//                        schedule(node);
//                    }
//                    node = next;
//                } catch (Throwable t) {
//                    node.setPreviousInVariableOrder(sentinel.getPreviousInVariableOrder());
//                    node.setNextInVariableOrder(next);
//                    sentinel.getPreviousInVariableOrder().setNextInVariableOrder(node);
//                    sentinel.setPreviousInVariableOrder(prev);
//                    throw t;
//                }
//            }
//        }
//    }
//
//    private static class NodeManager {
//
//        private volatile Node head;
//
//        private volatile Node tail;
//
//        private AtomicReferenceFieldUpdater<NodeManager,Node> TAIL_UPDATER
//                = AtomicReferenceFieldUpdater.newUpdater(NodeManager.class, Node.class, "tail");
//
//        public boolean casTail(Node oldNode, Node node) {
//            Node oldTail = tail;
//            if (TAIL_UPDATER.compareAndSet(this, oldNode, node)) {
//                if (oldTail == null) {
//                    //将head 节点指向tail节点
//                    head = tail;
//                }else {
//                    oldNode.next = node;
//                }
//
//                tail.prev = oldNode;
//                return true;
//            }
//
//            return false;
//        }
//
//    }
//
//
//    @RequiredArgsConstructor
//    private static class Node {
//
//        private static final Node DUMMY = new Node(null, 0L);
//
//
//        /**
//         * 下一个节点
//         */
//        private volatile Node next;
//
//        /**
//         * 上一个节点
//         */
//        private volatile Node prev;
//
//
//        private final String taskName;
//
//        //高8位
//        private final long delayMs;
//    }
//
//
//}
