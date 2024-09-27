package org.reduce.server.raft;

import com.alipay.remoting.CustomSerializer;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.exception.DeserializationException;
import com.alipay.remoting.exception.SerializationException;
import com.alipay.remoting.rpc.RequestCommand;
import com.alipay.remoting.rpc.ResponseCommand;
import com.alipay.remoting.rpc.protocol.RpcRequestCommand;
import com.alipay.remoting.rpc.protocol.RpcResponseCommand;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import lombok.extern.slf4j.Slf4j;
import org.reduce.server.raft.utiils.ProcessorRegistryCache;

@Slf4j
public class RequestProtobufSerializer implements CustomSerializer {

    public static final RequestProtobufSerializer INSTANCE = new RequestProtobufSerializer();

    @Override
    public <T extends RequestCommand> boolean serializeHeader(T request, InvokeContext invokeContext) throws SerializationException {
        return false;
    }

    @Override
    public <T extends ResponseCommand> boolean serializeHeader(T response) throws SerializationException {
        return false;
    }

    @Override
    public <T extends RequestCommand> boolean deserializeHeader(T request) throws DeserializationException {
        return false;
    }

    @Override
    public <T extends ResponseCommand> boolean deserializeHeader(T response, InvokeContext invokeContext) throws DeserializationException {
        return false;
    }

    @Override
    public <T extends RequestCommand> boolean serializeContent(T request, InvokeContext invokeContext)
            throws SerializationException {
        final RpcRequestCommand cmd = (RpcRequestCommand) request;
        final Message msg = (Message) cmd.getRequestObject();
        cmd.setContent(msg.toByteArray());
        return true;
    }

    @Override
    public <T extends ResponseCommand> boolean serializeContent(T response) throws SerializationException {
        final RpcResponseCommand cmd = (RpcResponseCommand) response;
        final Message msg = (Message) cmd.getResponseObject();
        cmd.setContent(msg.toByteArray());
        return true;
    }

    @Override

    public <T extends RequestCommand> boolean deserializeContent(T request) throws DeserializationException {
        final RpcRequestCommand cmd = (RpcRequestCommand) request;
        final String className = cmd.getRequestClass();
        try {
            Parser<Message> parser = ProcessorRegistryCache.getParser(className);
            if (parser == null) {
                log.error("Cannot find parser for message class: {}", className);
                return false;
            }

            Message message = parser.parseFrom(cmd.getContent());
            cmd.setRequestObject(message);
        } catch (InvalidProtocolBufferException e) {
            log.error("Parse message error,cause is: ", e);
            return false;
        }

        return true;
    }

    @Override
    public <T extends ResponseCommand> boolean deserializeContent(T response, InvokeContext invokeContext) throws DeserializationException {
        final RpcResponseCommand cmd = (RpcResponseCommand) response;
        final String className = cmd.getResponseClass();
        try {
            Parser<Message> parser = ProcessorRegistryCache.getParser(className);
            if (parser == null) {
                log.error("Cannot find parser for message class: {}", className);
                return false;
            }

            Message message = parser.parseFrom(cmd.getContent());
            cmd.setResponseObject(message);
        } catch (InvalidProtocolBufferException e) {
            log.error("Parse message error,cause is: ", e);
            return false;
        }

        return true;
    }
}
