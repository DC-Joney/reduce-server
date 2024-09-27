package org.reduce.server.raft.server;

import com.alipay.remoting.ExtendedNettyChannelHandler;
import io.netty.channel.ChannelHandler;

import java.util.Collections;
import java.util.List;

/**
 * 用于添加对应的handler处理器
 */
public class ExtendedNettyChannelHandlerImpl implements ExtendedNettyChannelHandler {

    @Override
    public List<ChannelHandler> frontChannelHandlers() {
        return Collections.emptyList();
    }

    @Override
    public List<ChannelHandler> backChannelHandlers() {
        return Collections.emptyList();
    }

}
