package org.common.config;

public interface ClientConnectionPoolManager<T> extends ConnectionPoolManager{


    T findConnection(String origin);




}
