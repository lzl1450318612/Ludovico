package serviceProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import serviceDiscovery.ServiceDiscovery;
import serviceDiscovery.zk.ZkServiceDiscovery;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizilin
 */
@Service
public class ServiceProviderImpl implements ServiceProvider {

    @Value("ludovico.service.port")
    private int port = 12345;

    private final Map<String, Object> serviceMap;

    private final Set<String> registeredService;

    @Resource
    private ServiceDiscovery serviceDiscovery;

    public ServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
    }

    private void addService(String serviceName, Object service) {
        if (registeredService.contains(serviceName)) {
            return;
        }
        serviceMap.put(serviceName, service);
        registeredService.add(serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @Override
    public void publishService(String serviceName, Object service) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            serviceDiscovery.registerService(serviceName, new InetSocketAddress(host, port));
            addService(serviceName, service);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }
}
