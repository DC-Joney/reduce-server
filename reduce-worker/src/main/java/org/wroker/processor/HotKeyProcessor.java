package org.wroker.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import lombok.RequiredArgsConstructor;
import org.common.raft.HotKeyRequest;
import org.wroker.reduce.KeyReduceManager;

@RequiredArgsConstructor
public class HotKeyProcessor extends AsyncUserProcessor<HotKeyRequest> {


    private KeyReduceManager reduceManager;


    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, HotKeyRequest request) {
        reduceManager.computeKey(request);
    }

    @Override
    public String interest() {
        return HotKeyRequest.class.getName();
    }
}
