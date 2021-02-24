package loadbalance;

import utils.RandomUtils;

import java.util.List;

/**
 * 随机负载均衡策略
 *
 * @author lizilin
 */
public class RadomStrategy implements LoadBalanceStrategy {

    @Override
    public String choose(List<String> serviceList) {
        return RandomUtils.getRandomResult(serviceList);
    }
}
