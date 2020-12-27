package serviceDiscovery;

import java.net.InetSocketAddress;

/**
 * @author lizilin
 */
public interface ServiceDiscovery {


    /**
     * 注册服务
     *
     * @param serviceName    服务名
     * @param serviceAddress 地址
     */
    void registerService(String serviceName, InetSocketAddress serviceAddress);

    /**
     * 注销服务
     *
     * @param serviceName
     */
    void deregisterService(String serviceName);

    /**
     * 根据服务名查找服务地址
     *
     * @param serviceName
     * @return
     */
    InetSocketAddress searchService(String serviceName);

}
