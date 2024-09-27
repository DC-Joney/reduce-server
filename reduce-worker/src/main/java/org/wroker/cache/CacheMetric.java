package org.wroker.cache;

import java.util.concurrent.atomic.LongAdder;

public class CacheMetric {

    private final LongAdder counter = new LongAdder();


    public void addCount(int count) {
        counter.add(count);
    }


    public long getCounter() {
        return counter.sum();
    }

    public void reset() {
        counter.reset();
    }
}
