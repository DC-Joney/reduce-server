package org.wroker.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.RemotingAddressParser;
import com.alipay.remoting.Url;
import com.alipay.remoting.rpc.RpcAddressParser;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import org.common.rpc.ServerChangeRequest;
import org.common.config.ConfigManager;
import org.wroker.Constant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 当server节点改变时，触发
 */
@Deprecated
public class SererChangeProcessor extends AsyncUserProcessor<ServerChangeRequest> {

    private final RemotingAddressParser addressParser = new RpcAddressParser();

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ServerChangeRequest request) {
        List<Url> newServerUrls = request.getServersList()
                .stream()
                .map(addressParser::parse)
                .collect(Collectors.toList());

        ConfigManager.setProperty(Constant.SERVER_PROPERTY_NAME, newServerUrls);
    }

    @Override
    public String interest() {
        return ServerChangeRequest.class.getName();
    }
}
