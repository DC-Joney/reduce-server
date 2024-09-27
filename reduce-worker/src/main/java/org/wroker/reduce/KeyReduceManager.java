package org.wroker.reduce;

import com.lmax.disruptor.RingBuffer;
import com.turing.common.utils.SystemClock;
import org.common.model.KeyRule;
import org.common.raft.HotKeyRequest;
import org.wroker.manager.KeyRuleManager;
import org.wroker.cache.Cache;
import org.wroker.cache.CacheLeapWindow;
import org.wroker.cache.LocalCacheFactory;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class KeyReduceManager {

    private final Cache<String, CacheLeapWindow> windowCache;

    private KeyRuleManager ruleManager;

    private RingBuffer<HotKeyNoticeEvent> ringBuffer;

    public KeyReduceManager() {
        this.windowCache = LocalCacheFactory.<String, CacheLeapWindow>builder("window cache")
                .scheduler(Executors.newSingleThreadScheduledExecutor())
                .initialSize(1 << 10)
                .maximumSize(1L << 31)
                .executor(Executors.newCachedThreadPool())
                .build();
    }

    public void computeKey(HotKeyRequest request) {
        String appName = request.getAppName();
        int count = request.getCount();

        KeyRule keyRule = ruleManager.findKeyRule(appName, request.getKey());
        //没有对应的规则则直接返回，不做任何计算
        if (keyRule == KeyRule.EMPTY) {
            return;
        }

        CacheLeapWindow leapArray = windowCache.get(appName,
                key -> new CacheLeapWindow((int) keyRule.getIntervalTimeMs(), keyRule.getSampleCount()),
                keyRule.getIntervalTimeMs(), TimeUnit.MILLISECONDS);

        leapArray.addCount(SystemClock.now(), count);
        long requestCounts = leapArray.sumCount();

        if (requestCounts > keyRule.getThreshold()) {
            //发布时间到disruptor中
            ringBuffer.publishEvent((event, sequence) -> event.setHotKey(request.getKey())
                    .setCurrentTime(SystemClock.now())
                    .setCount(requestCounts)
                    .setIntervalMs(keyRule.getIntervalTimeMs())
                    .setAppName(request.getAppName()));
        }


    }
}
