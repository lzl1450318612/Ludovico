package transport;

import pojo.InvokeInfo;
import pojo.InvokeResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @author lizilin
 */
public interface Sender {

    /**
     * 发送rpc请求
     */
    CompletableFuture<InvokeResponse> sendRpcRequest(InvokeInfo invokeInfo);

}
