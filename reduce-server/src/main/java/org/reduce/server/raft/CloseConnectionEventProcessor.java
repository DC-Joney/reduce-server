package org.reduce.server.raft;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import lombok.RequiredArgsConstructor;
import org.reduce.server.raft.manager.AppConnectionManager;

@RequiredArgsConstructor
public class CloseConnectionEventProcessor implements ConnectionEventProcessor {

    private final AppConnectionManager manager;

    @Override
    public void onEvent(String remoteAddress, Connection connection) {
        manager.removeConnection(connection);
    }
}
