package transport.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pojo.InvokeInfo;
import pojo.InvokeResponse;
import serviceDiscovery.ServiceDiscovery;
import serviceDiscovery.nacos.NacosServiceDiscovery;
import serviceDiscovery.zk.ZkServiceDiscovery;
import transport.Sender;
import transport.common.ChannelProvider;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizilin
 */
@Data
@Component
@Slf4j
public class NettyClientSender implements Sender {

    @Value("ludovico.service.registryCenter")
    private String registryCenter = "zookeeper";

    @Resource(type = ZkServiceDiscovery.class)
    private ServiceDiscovery zkServiceDiscovery;

    @Resource(type = NacosServiceDiscovery.class)
    private ServiceDiscovery nacosServiceDiscovery;

    @Resource
    private ChannelProvider channelProvider;

    Map<String, CompletableFuture<InvokeResponse>> requestMap = new ConcurrentHashMap<>();


    @Override
    public CompletableFuture<InvokeResponse> sendRpcRequest(InvokeInfo invokeInfo) {

        CompletableFuture<InvokeResponse> invokeFuture = new CompletableFuture<>();
        addToRequestMap(invokeInfo.getServiceName(), invokeFuture);
        String serviceName = invokeInfo.getServiceName();
        InetSocketAddress serviceAddress;

        if (StringUtils.equals(registryCenter, "nacos")) {
            serviceAddress = nacosServiceDiscovery.searchService(serviceName);
        } else {
            serviceAddress = zkServiceDiscovery.searchService(serviceName);
        }

        Channel channel = channelProvider.getChannel(serviceAddress);
        if (channel != null && channel.isActive()) {
            requestMap.put(serviceName, invokeFuture);
            channel.writeAndFlush(invokeInfo).addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    RequestPool.put(invokeInfo.getId(), invokeFuture);
                    log.info("Send rpc request success!");
                } else {
                    channelFuture.channel().close();
                    invokeFuture.completeExceptionally(channelFuture.cause());
                    log.error("Fail to send rpc request!Method name:{}", invokeInfo.getServiceName() + "." + invokeInfo.getMethodName());
                }
            });
        }else {
            log.error("Failed to get channel!");
        }
        return invokeFuture;
    }

    private void addToRequestMap(String serviceName, CompletableFuture<InvokeResponse> invokeFuture) {
        requestMap.put(serviceName, invokeFuture);
    }
}
