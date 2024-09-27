package org.reduce.server.raft.event;

import com.turing.common.notify.Event;
import lombok.Getter;

public class WorkerEvent extends Event {

    private final String workerId;

    @Getter
    private final WorkerState state;

    public WorkerEvent(String workerId, WorkerState state) {
        this.workerId = workerId;
        this.state = state;
    }

    public String getWorkerId() {
        return workerId;
    }

    public enum WorkerState {
        CONNECTED,

        DISCONNECT;
    }
}
