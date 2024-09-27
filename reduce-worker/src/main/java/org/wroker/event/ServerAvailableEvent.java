package org.wroker.event;

import com.turing.common.notify.SlowEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ServerAvailableEvent extends SlowEvent {

    private final AvailableState state ;

    public enum AvailableState {

        ADD_SERVERS,

        NOT_SERVERS,

        NONE;

    }
}
