package org.reduce.server.raft;

import cn.hutool.core.util.StrUtil;

/**
 *  raft 存储处理异常
 * @author zy
 */
public class RaftStoreException extends RuntimeException{

    public RaftStoreException(String message, Object...args) {
        super(StrUtil.format(message,args));
    }

    public RaftStoreException(Throwable cause, String message, Object...args) {
        super(StrUtil.format(message, args), cause);
    }
}
