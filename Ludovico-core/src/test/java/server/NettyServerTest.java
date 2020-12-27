package server;

import org.junit.Assert;
import org.junit.Test;
import serviceDiscovery.zk.ZkServiceDiscovery;
import transport.server.NettyServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author lizilin
 * @date 2020/11/19 8:22 下午
 */
public class NettyServerTest {

    /**
     * 测试netty服务启动
     */
    @Test
    public void testNettyServerStart() {
        new NettyServer().start();
    }

    /**
     * 测试服务注册和获取
     * @throws UnknownHostException
     */
    @Test
    public void testRegisterServiceToZK() throws UnknownHostException {
        ZkServiceDiscovery zkServiceDiscovery = new ZkServiceDiscovery();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 12345);
        zkServiceDiscovery.registerService("zkTest", inetSocketAddress);
        Assert.assertEquals(inetSocketAddress.toString(), zkServiceDiscovery.searchService("zkTest").toString());
    }



}
