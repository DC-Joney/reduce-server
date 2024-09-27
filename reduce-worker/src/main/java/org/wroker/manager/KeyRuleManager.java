package org.wroker.manager;

import com.alipay.remoting.Connection;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.sofa.jraft.JRaftUtils;
import com.alipay.sofa.jraft.Lifecycle;
import com.alipay.sofa.jraft.util.timer.Timeout;
import com.alipay.sofa.jraft.util.timer.Timer;
import com.alipay.sofa.jraft.util.timer.TimerTask;
import com.turing.common.notify.NotifyCenter;
import com.turing.common.notify.listener.Subscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.common.config.ConnectionPoolManager;
import org.common.model.KeyRule;
import org.common.rpc.KeyRuleRequest;
import org.wroker.event.KeyRuleEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 所有的keyRules 规则管理
 */
@Slf4j
@RequiredArgsConstructor
public class KeyRuleManager implements Subscriber<KeyRuleEvent>, Lifecycle<Void> {

    /**
     * 存储所有的key规则
     */
    private final Map<String /*app+key*/, KeyRule> ruleMap = new ConcurrentHashMap<>();

    private final ConnectionPoolManager poolManager;

    private static final Timer LOAD_RULES_TIMER = JRaftUtils.raftTimerFactory().createTimer("");

    @Override
    public boolean init(Void opts) {
        NotifyCenter.registerSubscriber(this);
        LOAD_RULES_TIMER.newTimeout(new FetchRulesTask(), 1000, TimeUnit.MILLISECONDS);
        return true;
    }


    @Override
    public void shutdown() {
        ruleMap.clear();
    }

    @Override
    public void onEvent(KeyRuleEvent ruleEvent) {
        addAllRules(ruleEvent.getKeyRules());
    }


    public Set<String> allKeys() {
        return ruleMap.keySet();
    }

    public void addRule(KeyRule keyRule) {
        String hotKey = keyRule.getAppName() + ":" + keyRule.getKey();
        ruleMap.put(hotKey, keyRule);
    }

    /**
     * 查找key对应的规则
     *
     * @param appName app名称
     * @param key     key
     */
    public KeyRule findKeyRule(String appName, String key) {
        String hotKey = appName + ":" + key;
        KeyRule keyRule = ruleMap.get(hotKey);
        if (keyRule == null) {
            //从server端拉取规则
            keyRule = fetchRuleFromServer(hotKey);
            if (keyRule != null) {
                //添加规则到ruleMap，等待下次拉取
                addRule(keyRule);
                return keyRule;
            }
        }

        return KeyRule.EMPTY;
    }

    public KeyRule fetchRuleFromServer(String hotKey) {
        List<KeyRule> keyRules = fetchRulesFromServer(Collections.singletonList(hotKey));
        if (keyRules != null && !keyRules.isEmpty()) {
            return keyRules.get(0);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public List<KeyRule> fetchRulesFromServer(Collection<String> hotKey) {
        //当拉取失败后进行重试，可能是由于网络失败的原因所导致的
        for (int i = 0; i < 2; i++) {
            try {
                //这里每次获取的connection是不一样的
                Connection connection = poolManager.findOneConnection(null);
                RpcServer rpcServer = (RpcServer) connection.getAttribute("client");
                KeyRuleRequest.Builder builder = KeyRuleRequest.newBuilder();
                hotKey.forEach(builder::addKeys);
                return (List<KeyRule>) rpcServer.invokeSync(connection, builder.build(), 2000);
            } catch (Exception e) {
                log.error("Fetch rules from server failed, cause is", e);
            }
        }

        return null;
    }


    private void addAllRules(List<KeyRule> allRules) {

    }

    @Override
    public Class<? extends KeyRuleEvent> subscribeType() {
        return KeyRuleEvent.class;
    }


    /**
     * 定时从server端拉取key的规则，包括key规则更新，由于体量比较大，所以间隔时间会比较长
     */
    private class FetchRulesTask implements TimerTask {

        @Override
        public void run(Timeout timeout) throws Exception {
            Set<String> allKeys = allKeys();
            List<KeyRule> keyRules = fetchRulesFromServer(allKeys);
            keyRules.forEach(KeyRuleManager.this::addRule);
            LOAD_RULES_TIMER.newTimeout(this, 10, TimeUnit.SECONDS);
        }
    }


}
