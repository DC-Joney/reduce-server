package org.wroker;

import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LocalCacheTest {

    public static void main(String[] args) throws InterruptedException {

        Scheduler scheduler = Scheduler.forScheduledExecutorService(Executors.newSingleThreadScheduledExecutor());


        Cache<String, String> cache = Caffeine.<String, String>newBuilder()
                .scheduler(scheduler)
                .executor(Executors.newCachedThreadPool())
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(@Nullable String key, @Nullable String value, @NonNull RemovalCause cause) {
                        System.out.println("remove：" + key + ", removeCause: " + cause.name());
                    }
                }).expireAfter(new Expiry<String, String>() {
                    @Override
                    public long expireAfterCreate(@NonNull String key, @NonNull String value, long currentTime) {
                        return currentTime;
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull String key, @NonNull String value, long currentTime, @NonNegative long currentDuration) {
                        System.out.println("expireAfterUpdate： " + key);
                        System.out.println("expireAfterUpdate time： " + (currentDuration >> 20));

                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(@NonNull String key, @NonNull String value, long currentTime, @NonNegative long currentDuration) {
                        System.out.println("expireAfterRead： " + key);
                        System.out.println("expireAfterRead time： " + (currentDuration >> 20));
                        return currentDuration;
                    }
                }).build();

        Optional<Policy.VarExpiration<String, String>> expirationOptional = cache.policy().expireVariably();

        Policy.VarExpiration<String, String> expiration = expirationOptional.get();



        expiration.putIfAbsent("1","2", Duration.ofSeconds(3));
//        expiration.putIfAbsent("2","2", Duration.ofSeconds(3));
//
//        TimeUnit.SECONDS.sleep(4);
//
//        expiration.putIfAbsent("4","2", Duration.ofSeconds(3));
//
//        TimeUnit.SECONDS.sleep(1);
//        expiration.put("4","3",Duration.ofSeconds(4));
//
//        cache.getIfPresent("4");



//        cache.asMap().keySet().forEach(key -> System.out.println(key));

    }
}
