package org.wroker;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mapdb.*;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.WriteOptions;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DBTest {

    public static void main(String[] args) throws InterruptedException {
        DB mapdb = DBMaker.memoryDirectDB()
                .concurrencyScale(16)
                .executorEnable()
                .make();




        HTreeMap<String, String> map = mapdb.<String,String>hashMap("test", Serializer.STRING,Serializer.STRING)
                .counterEnable()
                .expireAfterCreate(3, TimeUnit.MILLISECONDS)
                .expireAfterUpdate(3,TimeUnit.MILLISECONDS)
                .expireExecutor(Executors.newSingleThreadScheduledExecutor())
                .expireExecutorPeriod(500)
                .modificationListener(new MapModificationListener<String, String>() {
                    @Override
                    public void modify(@NotNull String key, @Nullable String oldValue, @Nullable String newValue, boolean triggered) {
                        log.info("key: {}, oldValue: {}, newValue:{}",key,oldValue,newValue);
                    }
                })
                .createOrOpen();

        map.put("1","2");


        TimeUnit.SECONDS.sleep(3);

        System.out.println(map.get("1"));




    }
}
