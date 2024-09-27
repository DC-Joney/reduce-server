//package org.example;
//
//import io.etcd.jetcd.*;
//import io.etcd.jetcd.election.CampaignResponse;
//import io.etcd.jetcd.election.LeaderKey;
//import io.etcd.jetcd.kv.GetResponse;
//import io.etcd.jetcd.kv.PutResponse;
//import io.etcd.jetcd.op.Cmp;
//import io.etcd.jetcd.options.GetOption;
//import io.grpc.*;
//
//import java.nio.charset.Charset;
//import java.time.Duration;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@SuppressWarnings("all")
//public class EtcdExample {
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
//
//        KV kvClient = client.getKVClient();
//
//        CompletableFuture<PutResponse> put = kvClient.put(ByteSequence.from("test", Charset.defaultCharset()),
//                ByteSequence.from("value", Charset.defaultCharset()));
//        PutResponse join = put.join();
//
//        Lease leaseClient = client.getLeaseClient();
//
//
//        GetResponse test = kvClient.get(ByteSequence.from("test", Charset.defaultCharset()), GetOption.newBuilder().build()).join();
//        System.out.println(test.getHeader().getRaftTerm());
//        List<KeyValue> kvs = test.getKvs();
//
//        for (KeyValue kv : kvs) {
//            System.out.println(kv.getKey().toString(Charset.defaultCharset()));
//        }
//
//
//        Election electionClient = client.getElectionClient();
//
//        ByteSequence electionKey = setValue("electionKey");
//
//        ByteSequence electionValue = setValue("electionValue");
//        electionClient.campaign(electionKey, 0, electionValue);
//
//        ByteSequence electionValue1 = setValue("electionValue1");
//
//        CampaignResponse join1 = electionClient.campaign(electionKey, 1, electionValue1).join();
//
//        LeaderKey leader = join1.getLeader();
//        System.out.println(leader);
//
////        client.getKVClient().get(GetOption.newBuilder().)
//
//        client.close();
//
//    }
//
//    static ByteSequence setValue(String value) {
//        return ByteSequence.from(value, Charset.defaultCharset());
//    }
//}
