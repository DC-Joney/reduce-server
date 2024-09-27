package org.reduce.server.raft.event;

import lombok.Getter;
import lombok.Setter;
import org.common.raft.HotKeyRequest;

@Setter
@Getter
public class HotKeyEvent {

    /**
     * hot key的元数据信息
     */
    HotKeyRequest requestMeta;

    /**
     * key 对应的worker节点
     */
    private String workerId;
}
