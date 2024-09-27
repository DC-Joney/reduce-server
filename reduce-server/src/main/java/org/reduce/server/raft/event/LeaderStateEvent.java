package org.reduce.server.raft.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LeaderStateEvent {

    private final LeaderState state;

    public enum LeaderState {
        LEADER_START,

        LEADER_STOP;

    }

}
