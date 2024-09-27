//package org.example;
//
//import io.etcd.jetcd.Client;
//import io.etcd.jetcd.Txn;
//import io.etcd.jetcd.op.Cmp;
//import io.grpc.*;
//
//public class EtcdExample2 {
//
//
//    public static void main(String[] args) {
//        Client client = Client.builder().endpoints("http://localhost:12379").interceptor(new ClientInterceptor() {
//                    @Override
//                    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
//                        return channel.newCall(methodDescriptor, callOptions);
//                    }
//                })
//                .header("2", "3")
//                .build();
//
//        Txn txn = client.getKVClient().txn();
//
//
//
//    }
//}
