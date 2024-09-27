package org.reduce.server.raft.utiils;

import cn.hutool.core.util.ClassUtil;
import com.alipay.remoting.CustomSerializerManager;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.turing.common.annotaion.Nullable;
import lombok.experimental.UtilityClass;
import org.reduce.server.raft.RequestProtobufSerializer;
import org.reduce.server.raft.handler.MessageProcessor;
import org.raft.rpc.Response;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class ProcessorRegistryCache {

    private final AtomicInteger typeCounter = new AtomicInteger();

    private final Map<Integer, Message> typeCache = new HashMap<>();

    private final Map<String, Integer> typeConverters = new ConcurrentHashMap<>();

    private final Map<Integer, MessageProcessor<? extends Message, ? extends Message>> processorCache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends Message> Parser<T> getParser(int messageType) {
//        int messageType = ProtoUtils.getType(flag);
        Message message = typeCache.get(messageType);
        return (Parser<T>) message.getParserForType();
    }

    /**
     * @param message 将对应的消息类型转为 int type
     * @return 返回 message 对应的type 值
     */
    @NonNull
    public int findType(Message message) {
        //获取对应的class名称
        String className = ClassUtil.getClassName(message, false);
        //记录当前messageType的类型
        int messageType = typeConverters.computeIfAbsent(className, key -> typeCounter.getAndIncrement() << 1);
        //通过type将其放入到typeCache中
        typeCache.putIfAbsent(messageType, message);
        return messageType;
    }

    public void registerMessageType(Message message) {
        findType(message);
        CustomSerializerManager.registerCustomSerializer(message.getClass().getName(), RequestProtobufSerializer.INSTANCE);
    }


    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Message> Parser<T> getParser(String className){
        Integer classType = typeConverters.get(className);
        if (classType == null) {
            return null;
        }

        return (Parser<T>) typeCache.get(classType).getParserForType();

    }

    /**
     * 返回message 对应的类型
     *
     * @param message message
     */
    public int markWrite(Message message) {
        int originType = ProcessorRegistryCache.findType(message);
        //低1位用来存储当前message是可写还是可读类型的，write类型的低1位为1
        int newType = originType | 1;
        //设置当前message的type
        ProcessorRegistryCache.setMessageType(message, newType);

        if (processorCache.containsKey(originType)) {
            MessageProcessor<? extends Message, ? extends Message> processor = processorCache.remove(originType);
            processorCache.put(newType, processor);
        }
        return newType;
    }

    /**
     * 将message设置为可读类型，可读类型的低 1位 = 0
     *
     * @param message message
     */
    public int markRead(Message message) {
        int newType = ProcessorRegistryCache.findType(message) & ~1;
        ProcessorRegistryCache.setMessageType(message, newType);
        return newType;
    }

    /**
     * @param message 将对应的消息类型转为 int type
     * @return 返回 message 对应的type 值
     */
    @NonNull
    int setMessageType(Message message, int newType) {
        int messageType = findType(message);
        typeCache.put(newType, message);
        typeConverters.put(message.getClass().getName(), newType);
        return messageType;
    }

    /**
     * 注册message 对应的processor 处理器
     *
     * @param message   message
     * @param processor processor 处理器
     */
    public <REQ extends Message, RESP extends Message> void registerProcessor(Message message, MessageProcessor<REQ, RESP> processor) {
        int messageType = findType(message);
        processorCache.put(messageType, processor);
    }

    /**
     * 返回message对应的处理器
     *
     * @param message message
     */
    @SuppressWarnings("unchecked")
    public <REQ extends Message, RESP extends Message> MessageProcessor<REQ, RESP> getProcessor(Message message) {
        int messageType = findType(message);
        return (MessageProcessor<REQ, RESP>) processorCache.getOrDefault(messageType, new MessageProcessor<Message, Message>() {
            @Override
            public Message onMessage(Message message) {
                return Response.newBuilder()
                        .setSuccess(false)
                        .setErrMsg("Cannot find type " + messageType + " supported processor")
                        .build();
            }
        });
    }


}
