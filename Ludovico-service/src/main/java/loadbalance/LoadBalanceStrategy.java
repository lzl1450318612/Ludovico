package loadbalance;

import java.util.List;

/**
 * 负载均衡策略
 *
 * @author lizilin
 */
public interface LoadBalanceStrategy {

    String execute(List<String> serviceList);


}
