package loadbalance;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询负载均衡策略
 *
 * @author lizilin
 */
public class LoopStrategy implements LoadBalanceStrategy {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public String choose(List<String> serviceList) {
        if (CollectionUtils.isEmpty(serviceList)) {
            return "";
        }
        return serviceList.get(atomicInteger.getAndIncrement() % serviceList.size());
    }
}
