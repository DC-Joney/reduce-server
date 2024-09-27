package org.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyRule  {

    public static final KeyRule EMPTY = new KeyRule();

    /**
     * appName名称
     */
    private String appName;

    /**
     * 规则对应的key
     */
    private String key;

    /**
     * 监测的时间,单位为：ms
     */
    private long intervalTimeMs;

    /**
     * 分割的窗口数, 默认为16个窗口
     */
    private int sampleCount = 16;

    /**
     * key需要计算的的阈值
     */
    private int threshold;
}

