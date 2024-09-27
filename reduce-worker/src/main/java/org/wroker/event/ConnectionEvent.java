package org.wroker.event;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventType;
import com.turing.common.notify.Event;
import com.turing.common.notify.SlowEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class ConnectionEvent extends SlowEvent {

    private final ConnectionEventType eventType;

    private final Connection connection;

    private String remoteAddress;

    /**
     * 重试的次数
     */
    private int retryCount;

    /**
     * 判断是否是 关闭事件
     */
    public boolean isClosed() {
        return eventType == ConnectionEventType.CLOSE;
    }

    /**
     * 判断是否是 关闭事件
     */
    public boolean isConnected() {
        return eventType == ConnectionEventType.CONNECT;
    }

    public void incrementRetry() {
        retryCount++;
    }

    public String getRemoteAddress() {
        return connection.getRemoteAddress().toString();
    }
}
