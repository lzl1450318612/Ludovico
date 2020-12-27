package transport.client;

import lombok.extern.slf4j.Slf4j;
import pojo.InvokeResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizilin
 * @date 2020/11/9 2:55 下午
 */
@Slf4j
public class RequestPool {

    private static final Map<Long, CompletableFuture<InvokeResponse>> REQUEST_MAP = new ConcurrentHashMap<>();


    public static void put(Long requestId, CompletableFuture<InvokeResponse> future) {
        REQUEST_MAP.put(requestId, future);
    }

    public static void complete(InvokeResponse invokeResponse) {
        CompletableFuture<InvokeResponse> future = REQUEST_MAP.remove(invokeResponse.getId());
        future.complete(invokeResponse);
        log.info("Complete request!ID:{}", invokeResponse.getId());
    }


}
