package org.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReduceKeys {

    /**
     *  key所在的节点
     */
    private String localAddress;

    /**
     * 计算的所有key
     */
    private List<String> reduceKeys;

}
