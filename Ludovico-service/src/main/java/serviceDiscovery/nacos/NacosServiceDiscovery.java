package serviceDiscovery.nacos;

import org.springframework.stereotype.Service;
import serviceDiscovery.ServiceDiscovery;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author lizilin
 */
//@Service
public class NacosServiceDiscovery implements ServiceDiscovery {

    @Override
    public void registerService(String serviceName, InetSocketAddress serviceAddress) {

    }

    @Override
    public void deregisterService(String serviceName) {

    }

    @Override
    public InetSocketAddress searchService(String serviceName) {
        return null;
    }
}
