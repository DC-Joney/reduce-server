package org.wroker.event;

import com.alipay.remoting.InvokeCallback;
import com.alipay.remoting.rpc.RpcClient;
import com.turing.common.notify.NotifyCenter;
import com.turing.common.notify.listener.Subscriber;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.common.model.KeyRule;

import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
@Deprecated
public class PullRulesEventHandler implements Subscriber<ConnectionEvent> {

    private RpcClient rpcClient;

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void onEvent(ConnectionEvent connectionEvent) {
        if (connectionEvent.isConnected()) {

            PullKeyRulesRequest request = new PullKeyRulesRequest();
            rpcClient.invokeWithCallback(connectionEvent.getConnection(), request, new InvokeCallback() {
                @Override
                public void onResponse(Object result) {
                    List<KeyRule> allRules = (List<KeyRule>) result;
                    NotifyCenter.publishEvent(KeyRuleEvent.create(connectionEvent.getRemoteAddress(), allRules));
                }

                @Override
                public void onException(Throwable e) {

                    if (connectionEvent.getRetryCount() >= 3) {
                        log.error("Pull key rules fail,cause is:", e);
                    }

                    connectionEvent.incrementRetry();
                    NotifyCenter.publishEvent(connectionEvent);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            },1000);

        }

    }

    @Override
    public Class<? extends ConnectionEvent> subscribeType() {
        return ConnectionEvent.class;
    }
}
