package loadbalance;

import java.util.List;

/**
 * 权重负载均衡策略
 *
 * @author lizilin
 */
public class WeightStrategy implements LoadBalanceStrategy {
    @Override
    public String choose(List<String> serviceList) {
        return null;
    }
}
