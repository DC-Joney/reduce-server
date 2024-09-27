package org.reduce.server.raft.manager;

import com.google.common.collect.Lists;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import com.turing.common.notify.listener.Subscriber;
import org.common.config.ClientConnectionPoolManager;
import org.common.rpc.ReDistributionKeys;
import org.reduce.server.raft.RaftServer;
import org.reduce.server.raft.ResponseClosure;
import org.reduce.server.raft.event.WorkerEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

public class KeyMetaManager implements Subscriber<WorkerEvent> {

    private final Map<String/*key*/, String/*workerId*/> keyRouteTable = new ConcurrentHashMap<>();
    private final Map<String/*workerId*/, List<String>/*key*/> distributionTable = new ConcurrentHashMap<>();

    private final Set<String> workerIds = new ConcurrentSkipListSet<>();

    private final AtomicLong roundIndex = new AtomicLong();

    private final ClientConnectionPoolManager<WorkerConnection> workerManager;

    private RaftServer raftServer;

    public KeyMetaManager(ClientConnectionPoolManager<WorkerConnection> workerManager) {
        this.workerManager = workerManager;
    }


    /**
     * 为热点key分配 worker节点
     *
     * @param key 热点key
     */
    public String distributionKey(String appName, String hotKey) {
        String workerId = keyRouteTable.get(appName + "@" + hotKey);
        //说明该key未分配节点
        if (workerId == null) {
            Set<String> workerSet = workerManager.connectionKeys();
            int index = (int) (roundIndex.getAndIncrement() & workerSet.size());
            List<String> workers = Lists.newArrayList(workerSet);
            //计算的新得worker节点
            workerId = workers.get(index);
            keyRouteTable.putIfAbsent(hotKey, workerId);
            distributionTable.compute(workerId, (key, value) -> {
                if (value == null) {
                    value = new ArrayList<>();
                }

                value.add(key);
                return value;
            });
        }


        return workerId;
    }


    public void addKeyRoute(String hotKey, String workerId) {
        keyRouteTable.putIfAbsent(hotKey, workerId);
        distributionTable.compute(workerId, (key, value) -> {
            if (value == null) {
                value = new ArrayList<>();
            }

            value.add(hotKey);
            return value;
        });
    }


    public void addWorker(String workerId, String raftPeer) {
        workerIds.add(workerId);
    }


    /**
     * 将故障worker节点上的key平均分配到其他的节点
     *
     * @param workerId 故障的worker节点
     */
    public Map<String, List<String>> redistribution(String workerId) {
        //worker节点上计算的所有的key值
        List<String> allKeys = distributionTable.remove(workerId);
        //说明该节点未分配数据
        if (allKeys == null || allKeys.isEmpty()) {
            return Collections.emptyMap();
        }

        //现有存活的worker节点数量
        int workerCount = distributionTable.size();
        //现有存活的workerId
        Set<String> workerSet = distributionTable.keySet();
        List<String> workerIds = Lists.newArrayList(workerSet);

        //故障worker节点上的key 重新分配后的信息
        Map<String, List<String>> distributionKeys = new HashMap<>(workerSet.size());

        int count = 0;
        for (String distributionKey : allKeys) {
            int index = count % workerCount;
            //需要重新分配的workerId
            String distributionWorkerId = workerIds.get(index);
            distributionKeys.compute(distributionWorkerId, (key, value) -> {
                if (value == null) {
                    value = new ArrayList<>();
                }

                value.add(distributionKey);
                return value;
            });
            count++;
        }

        //TODO: 将重新分配后的key写入到keyRouteTable 以及 distributionTable
        return distributionKeys;
    }

    @Override
    public void onEvent(WorkerEvent event) {

        if (event.getState() == WorkerEvent.WorkerState.CONNECTED) {
            workerIds.add(event.getWorkerId());
            return;
        }

        String workerId = event.getWorkerId();
        Map<String, List<String>> redistribution = redistribution(workerId);

        ReDistributionKeys.Builder builder = ReDistributionKeys.newBuilder();

        redistribution.forEach((key,values)-> {
            ListValue.Builder builder1 = ListValue.newBuilder();
            values.forEach(hotKey -> builder1.addValues(Value.newBuilder().setStringValue(hotKey).build()));
            builder.putAllKeys(key, builder1.build());
        });


        ResponseClosure responseClosure = new ResponseClosure();

        //应用到状态机
        raftServer.applyMachine(builder.build(), responseClosure);
    }

    @Override
    public Class<? extends WorkerEvent> subscribeType() {
        return WorkerEvent.class;
    }
}
