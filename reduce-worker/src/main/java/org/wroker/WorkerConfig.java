package org.wroker;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * reduce worker 节点配置信息
 */
@Getter
@Setter
public class WorkerConfig {


    /**
     * worker节点的id
     */
    private String workerId;

    /**
     *  worker 端口
     */
    private int port = 9999;

    /**
     * 所有的reduceServers节点
     */
    private List<String> reduceServers;




}
