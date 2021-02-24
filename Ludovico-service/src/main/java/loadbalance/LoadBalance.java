package loadbalance;

import org.springframework.beans.factory.annotation.Value;
import utils.AddressUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author lizilin
 */
public class LoadBalance {

    @Value("ludovico.service.loadBalance.strategy")
    private String loadBalanceConfig = "random";

    public LoadBalance() {
        if ("random".equals(loadBalanceConfig)) {
            this.loadBalanceStrategy = new RadomStrategy();
        } else if ("loop".equals(loadBalanceConfig)) {
            this.loadBalanceStrategy = new LoopStrategy();
        } else if ("weight".equals(loadBalanceConfig)) {
            this.loadBalanceStrategy = new WeightStrategy();
        } else if ("hash".equals(loadBalanceConfig)) {
            this.loadBalanceStrategy = new HashStrategy();
        }
    }

    private LoadBalanceStrategy loadBalanceStrategy;

    public InetSocketAddress select(List<String> serviceList) {
        if (serviceList.size() == 1) {
            return AddressUtils.toInetSocketAddress(serviceList.get(0));
        }
        return AddressUtils.toInetSocketAddress(loadBalanceStrategy.choose(serviceList));
    }

}
