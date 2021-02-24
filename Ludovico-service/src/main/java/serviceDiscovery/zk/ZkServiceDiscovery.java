package serviceDiscovery.zk;

import loadbalance.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import serviceDiscovery.ServiceDiscovery;
import utils.SingletonUtils;
import utils.ZkUtils;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizilin
 */
@Slf4j
@Service
public class ZkServiceDiscovery implements ServiceDiscovery {

    private final LoadBalance loadBalance = SingletonUtils.getInstance(LoadBalance.class);


    Map<String, InetSocketAddress> serviceAddressMap = new ConcurrentHashMap<>();

    @Override
    public void registerService(String serviceName, InetSocketAddress serviceAddress) {
        if (serviceAddressMap.containsKey(serviceName)) {
            //TODO:多个服务节点提供同一服务名的服务是是否要再加个判断，如果不包含当前服务节点的ip就新加进去，用于多服务节点进行负载均衡
            log.warn("Service is already registered!Service name:{}", serviceName);
            return;
        }
        ZkUtils.createPersistentNode("/" + serviceName + serviceAddress + serviceAddress.toString());
        serviceAddressMap.put(serviceName, serviceAddress);
    }

    @Override
    public void deregisterService(String serviceName) {
        if (serviceAddressMap.containsKey(serviceName)) {
            serviceAddressMap.remove(serviceName);
        }
        ZkUtils.removePersistentNode("/" + serviceName);
    }

    @Override
    public InetSocketAddress searchService(String serviceName) {
        if (serviceAddressMap.containsKey(serviceName)) {
            return serviceAddressMap.get(serviceName);
        }
        List<String> serviceList = ZkUtils.getChildNodes("/" + serviceName);
        if (CollectionUtils.isEmpty(serviceList)) {
            log.warn("Service is not found from service register center!");
            return null;
        }
        return loadBalance.select(serviceList);
    }
}
