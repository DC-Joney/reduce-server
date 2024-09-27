package org.reduce.server.raft.handler;

import com.google.protobuf.Message;

public interface MessageProcessor<REQ extends Message,RESP extends Message> {

    RESP onMessage(REQ request);
}
