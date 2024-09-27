package org.reduce.server.raft;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RaftOptions {

    /**
     * 所有的集群成员信息
     */
    List<String> members;

    /**
     * raft groupId
     */
    private String groupId;


    /**
     * 是否同步写磁盘日志文件
     */
    private boolean sync = false;


    /**
     * raftLog 存储路径
     */
    private String logPath;

    /**
     * 是否开启监控统计
     */
    private boolean enableMetrics = true;

    /**
     * snapshot 自动保存的时间，以秒为单位
     */
    private int snapshotSaveSec = 30 * 60;

    /**
     * 选举的超时时间
     */
    private int electionTimeoutMs = 3000;

    /**
     * 选举定时器间隔会在指定时间之外随机的最大范围，默认1秒
     */
    private int maxElectionDelayMs = 1000;

}
