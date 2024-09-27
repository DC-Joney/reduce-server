package org.wroker.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.turing.common.notify.NotifyCenter;
import org.common.model.KeyRule;
import org.common.model.KeyRules;
import org.wroker.event.KeyRuleEvent;

import java.util.List;

/**
 * 用来接受 keyRules事件变更的
 */
public class KeyRuleProcessor extends AsyncUserProcessor<KeyRules> {

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, KeyRules request) {
        List<KeyRule> keyRules = request.getKeyRules();
        KeyRuleEvent keyRuleEvent = KeyRuleEvent.create(bizCtx.getRemoteAddress(), keyRules);
        NotifyCenter.publishEvent(keyRuleEvent);
    }

    @Override
    public String interest() {
        return KeyRules.class.getName();
    }
}
