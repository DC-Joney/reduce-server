package org.wroker.event;

import com.alipay.remoting.rpc.RpcClient;
import com.netflix.config.DynamicStringProperty;
import com.turing.common.notify.listener.Subscriber;
import com.turing.common.utils.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.common.raft.Response;
import org.common.rpc.WorkerReportRequest;
import org.wroker.manager.ConnectionPoolManager;

@Deprecated
@Slf4j
public class WorkerInfoReportEventHandler implements Subscriber<ConnectionEvent> {

    private RpcClient rpcClient;

    private ConnectionPoolManager manager;

    private final DynamicStringProperty workerIdProperty = new DynamicStringProperty("worker.id","");




    @Override
    public void onEvent(ConnectionEvent connectionEvent) {
        if (connectionEvent.isConnected()) {
            WorkerReportRequest request = WorkerReportRequest.newBuilder()
                    .setWorkerId(workerIdProperty.getValue())
                    .setStartTime(SystemClock.now()).build();

            try {
                //将当前节点的信息上报给reduce server
                Response response = (Response) rpcClient.invokeSync(connectionEvent.getConnection(), request, 2000);
            }catch (Exception e) {
                log.error("Exception", e);
            }

        }
    }

    @Override
    public Class<? extends ConnectionEvent> subscribeType() {
        return ConnectionEvent.class;
    }
}
