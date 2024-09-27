package org.wroker.event;

import com.turing.common.notify.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.common.model.KeyRule;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class KeyRuleEvent extends Event {


    /**
     * 数据来源的节点url
     */
    private String originServer;


    /**
     * 所有的key规则信息
     */
    private List<KeyRule> keyRules;

}
