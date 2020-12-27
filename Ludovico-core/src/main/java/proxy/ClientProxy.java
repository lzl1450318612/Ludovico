package proxy;

import enums.MessageType;
import pojo.InvokeInfo;
import pojo.InvokeResponse;
import transport.client.NettyClientSender;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * @author lizilin
 */
public class ClientProxy implements InvocationHandler {

    @Resource
    private NettyClientSender nettyClientSender;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvokeInfo invokeInfo = InvokeInfo.builder()
                .methodName(method.getName())
                .args(args)
                .argsTypes(method.getParameterTypes())
                .messageType(MessageType.NORMAL)
                .build();
        CompletableFuture<InvokeResponse> future = nettyClientSender.sendRpcRequest(invokeInfo);
        InvokeResponse response = future.get();

        return response.getData();
    }
}
