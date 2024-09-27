package org.reduce.server.raft;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.DefaultConnectionManager;
import com.alipay.remoting.config.BoltGenericOption;
import com.alipay.sofa.jraft.*;
import com.alipay.sofa.jraft.closure.ReadIndexClosure;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.core.CliServiceImpl;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.entity.Task;
import com.alipay.sofa.jraft.error.RaftError;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.option.RpcOptions;
import com.alipay.sofa.jraft.rpc.*;
import com.alipay.sofa.jraft.rpc.impl.BoltRaftRpcFactory;
import com.alipay.sofa.jraft.rpc.impl.BoltRpcServer;
import com.alipay.sofa.jraft.util.BytesUtil;
import com.alipay.sofa.jraft.util.Endpoint;
import com.alipay.sofa.jraft.util.timer.Timeout;
import com.alipay.sofa.jraft.util.timer.Timer;
import com.alipay.sofa.jraft.util.timer.TimerTask;
import com.google.protobuf.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.common.raft.Response;
import org.reduce.server.raft.handler.MessageProcessor;
import org.reduce.server.raft.manager.AppConnectionManager;
import org.reduce.server.raft.server.ExtendedNettyChannelHandlerImpl;
import org.reduce.server.raft.utiils.DiskUtils;
import org.reduce.server.raft.utiils.ProcessorRegistryCache;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RaftServer implements Lifecycle<RaftOptions> {

    private NodeOptions nodeOptions;

    private Node raftNode;

    private RpcServer rpcServer;


    private RpcClient rpcClient;


    private CliService cliService;

    private CliClientService cliClientService;


    private final PeerId selfPeer;


    private StateMachine stateMachine;

    private RaftGroupService groupService;

    @Getter
    private RaftRpcFactory rpcFactory;

    private final Timer refreshTimer;
    private final Timer joinTimer;


    @Getter
    private AppConnectionManager connectionManager;


    public RaftServer(String selfPeer) {
        this(PeerId.parsePeer(selfPeer));
    }

    public RaftServer(PeerId selfPeer) {
        this.selfPeer = selfPeer;
        this.refreshTimer = JRaftUtils.raftTimerFactory().createTimer("Refresh route table timer");
        this.joinTimer = JRaftUtils.raftTimerFactory().createTimer("Join raft cluster timer");
    }

    @Override
    public boolean init(RaftOptions opts) {
        rpcFactory = new BoltRaftRpcFactory();

        CliOptions cliOptions = new CliOptions();
        cliOptions.setTimeoutMs(1000);
        cliOptions.setMaxRetry(3);

        //初始化cliService
        this.cliService = RaftServiceFactory.createAndInitCliService(cliOptions);
        this.cliClientService = ((CliServiceImpl) this.cliService).getCliClientService();

        //初始化rpcClient
        this.rpcClient = rpcFactory.createRpcClient();
        RpcOptions rpcOptions = new RpcOptions();
        rpcOptions.setRpcProcessorThreadPoolSize(2);
        rpcClient.init(rpcOptions);

        //创建rpcServer
        this.rpcServer = rpcFactory.createRpcServer(selfPeer.getEndpoint());
        initRpcServer(rpcServer);


        this.nodeOptions = new NodeOptions();

        this.stateMachine = new CacheStateMachine();
        nodeOptions.setFsm(stateMachine);

        //添加所有的成员信息
        Configuration configuration = initMembers(opts);
        //更新路由信息
        RouteTable.getInstance().updateConfiguration(opts.getGroupId(), configuration);
        //添加成员信息
        nodeOptions.setInitialConf(configuration);
        com.alipay.sofa.jraft.option.RaftOptions options = new com.alipay.sofa.jraft.option.RaftOptions();
        options.setSync(opts.isSync());
        options.setMaxElectionDelayMs(opts.getMaxElectionDelayMs());
        nodeOptions.setRaftOptions(options);
        //初始化存储目录
        initDirectory(opts.getLogPath(), opts.getGroupId(), nodeOptions);

        //设置使用全局的timer
        nodeOptions.setSharedVoteTimer(true);
        nodeOptions.setSharedElectionTimer(true);
        nodeOptions.setSharedSnapshotTimer(true);
        nodeOptions.setSharedStepDownTimer(true);

        //开启统计
        nodeOptions.setEnableMetrics(opts.isEnableMetrics());

        //设置snapshot间隔多久保存一次
        nodeOptions.setSnapshotIntervalSecs(opts.getSnapshotSaveSec());

        //设置选举的超时时间
        nodeOptions.setElectionTimeoutMs(opts.getElectionTimeoutMs());


        this.groupService = new RaftGroupService(opts.getGroupId(), selfPeer, nodeOptions, rpcServer);
        //启动rpcServer
        this.raftNode = this.groupService.start();


        int electionTimeoutMs = opts.getElectionTimeoutMs();
        int maxElectionDelayMs = opts.getMaxElectionDelayMs();
        int initDelayMs = refreshDelay(electionTimeoutMs, maxElectionDelayMs);
        refreshTimer.newTimeout(new RefreshRouteTask(electionTimeoutMs, maxElectionDelayMs), initDelayMs, TimeUnit.MILLISECONDS);
        joinTimer.newTimeout(new JoinClusterTask(), 2000, TimeUnit.MILLISECONDS);
        return true;
    }

    class JoinClusterTask implements TimerTask {
        @Override
        public void run(Timeout timeout) throws Exception {
            RouteTable routeTable = RouteTable.getInstance();
            String groupId = groupService.getGroupId();
            Configuration configuration = routeTable.getConfiguration(groupId);

            try {
                List<PeerId> peerIds = cliService.getPeers(groupId, configuration);
                log.info("Leader is: {}, AllPeers: {}", raftNode.getLeaderId(),  peerIds);
                if (peerIds.contains(selfPeer)) {
                    refreshTimer.newTimeout(this, 2, TimeUnit.SECONDS);
                    return;
                }
                Status status = cliService.addPeer(groupId, configuration, selfPeer);
                if (status.isOk()) {
                    return;
                }
                log.warn("Failed to join the cluster, retry...");
            } catch (Exception e) {
                log.error("Failed to join the cluster, retry...", e);
            }

            refreshTimer.newTimeout(this, 5, TimeUnit.SECONDS);
        }
    }

    private void joinCluster(Configuration configuration) {

    }

    /**
     * 初始化rpcServer
     */
    private void initRpcServer(RpcServer rpcServer) {
        com.alipay.remoting.rpc.RpcServer server = ((BoltRpcServer) rpcServer).getServer();
        DefaultConnectionManager manager = server.getConnectionManager();
        server.option(BoltGenericOption.EXTENDED_NETTY_CHANNEL_HANDLER, new ExtendedNettyChannelHandlerImpl());
        connectionManager = new AppConnectionManager(manager, server);
        CloseConnectionEventProcessor closeProcessor = new CloseConnectionEventProcessor(connectionManager);
        server.addConnectionEventProcessor(ConnectionEventType.CLOSE, closeProcessor);
        //添加處理器
        RpcRequestProcessors.addMessageProcessors(this, server);

        //添加raft自身的處理器
        RaftRpcServerFactory.addRaftRequestProcessors(rpcServer);

    }


    private ByteBuffer getDataBuffer(Message request) {
        int messageType = ProcessorRegistryCache.markWrite(request);
        byte[] bytes = request.toByteArray();
        ByteBuffer dataBuffer = ByteBuffer.allocate(Integer.BYTES + bytes.length);
        dataBuffer.putInt(messageType).put(bytes).flip();
        return dataBuffer;
    }


    public ResponseClosure doRead(Message request) {

        ResponseClosure closure = new ResponseClosure();

        raftNode.readIndex(BytesUtil.EMPTY_BYTES, new ReadIndexClosure() {
            @Override
            public void run(Status status, long index, byte[] reqCtx) {
                if (status.isOk()) {
                    MessageProcessor<Message, Message> processor = ProcessorRegistryCache.getProcessor(request);
                    Message message = processor.onMessage(request);
                    closure.setResponse(message);
                    closure.run(Status.OK());
                    return;
                }


                //如果当前节点是leader节点
                if (raftNode.isLeader()) {
                    applyMachine(request, closure);
                    return;
                }

                //路由到leader处理
                invokeToLeader(request, closure, 2000);

            }
        });

        return closure;
    }

    public void applyMachine(Message request, ResponseClosure closure) {
        Task task = new Task();
        ByteBuffer dataBuffer = getDataBuffer(request);
        task.setData(dataBuffer);
        task.setDone(new RaftClosure(request, new Closure() {
            @Override
            public void run(Status status) {
                log.info("Apply machine state for {}",request.getClass().getName());
                RaftClosure.RaftStatus raftStatus = (RaftClosure.RaftStatus) status;
                if (status.isOk()) {
                    closure.setResponse(raftStatus.getResponse());
                    closure.run(Status.OK());
                    return;
                }

                closure.setThrowable(raftStatus.getThrowable());
                closure.run(new Status(RaftError.UNKNOWN, "ERROR"));

            }
        }));
        raftNode.apply(task);
        //TODO 交给状态机处理
        return;
    }

    public void invokeToLeader(Message request, ResponseClosure closure, int timeoutMillis) {

        try {

            //从路由列表获取leaderIp地址
            Endpoint leaderIp = RouteTable.getInstance().selectLeader(raftNode.getGroupId()).getEndpoint();

            rpcClient.invokeAsync(leaderIp, request, new InvokeCallback() {
                @Override
                public void complete(Object o, Throwable ex) {
                    if (Objects.nonNull(ex)) {
                        closure.setThrowable(ex);
                        closure.run(new Status(RaftError.UNKNOWN, ex.getMessage()));
                        return;
                    }
                    if (!((Response) o).getSuccess()) {
                        closure.setThrowable(new IllegalStateException(((Response) o).getErrMsg()));
                        closure.run(new Status(RaftError.UNKNOWN, ((Response) o).getErrMsg()));
                        return;
                    }
                    closure.setResponse((Response) o);
                    closure.run(Status.OK());
                }

                @Override
                public Executor executor() {
                    return Executors.newSingleThreadExecutor();
                }
            }, timeoutMillis);
        } catch (Exception e) {
            closure.setThrowable(e);
            closure.run(new Status(RaftError.UNKNOWN, e.toString()));
        }


    }

    public Node getRaftNode() {
        return raftNode;
    }

    public PeerId getSelfPeer() {
        return selfPeer;
    }

    /**
     * 返回智行任务的超时时间
     *
     * @param electionTimeoutMs  选举的超时时间
     * @param maxElectionDelayMs
     */
    private int refreshDelay(int electionTimeoutMs, int maxElectionDelayMs) {
        return electionTimeoutMs + ThreadLocalRandom.current().nextInt(maxElectionDelayMs + 1000, maxElectionDelayMs + 2000);
    }

    private Configuration initMembers(RaftOptions opts) {
        List<String> members = opts.getMembers();
        NodeManager nodeManager = NodeManager.getInstance();
        Configuration configuration = new Configuration();
        for (String member : members) {
            PeerId peerId = PeerId.parsePeer(member);
            nodeManager.addAddress(peerId.getEndpoint());
            configuration.addPeer(peerId);
        }

        return configuration;
    }



    public void initDirectory(String parentPath, String groupName, NodeOptions copy) {
        final String logUri = Paths.get(parentPath, groupName, "log").toString();
        final String snapshotUri = Paths.get(parentPath, groupName, "snapshot").toString();
        final String metaDataUri = Paths.get(parentPath, groupName, "meta-data").toString();

        // Initialize the raft file storage path for different services
        try {
            DiskUtils.forceMkdir(new File(logUri));
            DiskUtils.forceMkdir(new File(snapshotUri));
            DiskUtils.forceMkdir(new File(metaDataUri));
        } catch (Exception e) {
            log.error("Init Raft-File dir have some error, cause: ", e);
            throw new RuntimeException(e);
        }

        copy.setLogUri(logUri);
        copy.setRaftMetaUri(metaDataUri);
        copy.setSnapshotUri(snapshotUri);
    }

    public ResponseClosure doWrite(Message request) {
        log.info("dowWrite request {}", request.getClass().getName());
        ResponseClosure closure = new ResponseClosure();
        if (!raftNode.isLeader()) {
            invokeToLeader(request, closure, 2000);
            return closure;
        }

        applyMachine(request, closure);
        return closure;
    }

    @RequiredArgsConstructor
    class RefreshRouteTask implements TimerTask {

        private final int electionTimeoutMs;

        private final int maxElectionDelayMs;

        @Override
        public void run(Timeout timeout) throws Exception {

            RouteTable routeTable = RouteTable.getInstance();
            String groupName = groupService.getGroupId();
            Status status = null;
            try {
                Configuration oldConf = routeTable.getConfiguration(groupName);
                String oldLeader = Optional.ofNullable(routeTable.selectLeader(groupName)).orElse(PeerId.emptyPeer())
                        .getEndpoint().toString();
                // fix issue #3661  https://github.com/alibaba/nacos/issues/3661
                status = routeTable.refreshLeader(cliClientService, groupName, 2000);
                if (!status.isOk()) {
                    log.error("Fail to refresh leader for group : {}, status is : {}", groupName, status);
                }
                status = routeTable.refreshConfiguration(cliClientService, groupName, 2000);
                if (!status.isOk()) {
                    log.error("Fail to refresh route configuration for group : {}, status is : {}", groupName, status);
                }
            } catch (Exception e) {
                log.error("Fail to refresh raft metadata info for group : {}, error is : {}", groupName, e);
            }


            refreshTimer.newTimeout(this, refreshDelay(electionTimeoutMs, maxElectionDelayMs), TimeUnit.MILLISECONDS);
        }
    }

    public boolean isLeader() {
        return raftNode.isLeader();
    }

    @Override
    public void shutdown() {
        raftNode.shutdown();
        rpcServer.shutdown();
        rpcClient.shutdown();
        refreshTimer.stop();
    }
}
