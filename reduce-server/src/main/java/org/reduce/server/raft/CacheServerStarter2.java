package org.reduce.server.raft;

import io.vavr.collection.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheServerStarter2 {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                createRaftServer1("./cache1","localhost:8081");
            }
        });

//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                createRaftServer1("./cache2","localhost:8082");
//            }
//        });
//
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                createRaftServer1("./cache3","localhost:8083");
//            }
//        });



    }

    private static RaftServer createRaftServer1(String logPath, String selfPeer) {

        RaftOptions raftOptions = new RaftOptions();
        raftOptions.setGroupId("cache-server");
        raftOptions.setLogPath(logPath);
        raftOptions.setMembers(List.of("localhost:8081","localhost:8082","localhost:8083").asJava());

        RaftServer raftServer = new RaftServer(selfPeer);
        raftServer.init(raftOptions);
        return raftServer;
    }



}
