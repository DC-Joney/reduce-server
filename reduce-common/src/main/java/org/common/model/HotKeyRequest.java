package org.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotKeyRequest {

    /**
     * key对应的app
     */
    private String appName;

    /**
     * 要计算的key
     */
    private String key;

    /**
     * 客户端上报的key count
     */
    private int count;

}
