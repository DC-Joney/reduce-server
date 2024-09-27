package org.common.config;

import com.alipay.remoting.Connection;

import java.util.Set;

public interface ConnectionPoolManager  {


    /**
     * 添加connection 连接
     * @param origin connection 来源
     * @param connection connection instance
     */
    void addConnection(String origin, Connection connection);

    /**
     * 删除对应的connection
     * @param connection connection instance
     * @return  如果为true表示 connection 对应的origin的所有connection都已经被移除掉了
     */
    boolean remove(Connection connection);

    /**
     * 删除origin 对应的所有connection
     * @param origin connection 来源
     */
    void remove(String origin);


    /**
     *
     * 获取 origin 对应的 连接
     */
    Connection findOneConnection(String origin);


    Set<String> connectionKeys();

    /**
     * 连接池是否是空的
     */
    boolean isEmpty();

}
