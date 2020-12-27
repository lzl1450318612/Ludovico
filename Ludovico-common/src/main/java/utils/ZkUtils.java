package utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * zk工具类
 *
 * @author lizilin
 */
@Slf4j
public class ZkUtils {

    /**
     * zk根路径
     */
    @Value("ludovico.service.zk.rootPath")
    private static final String ZK_ROOT_PATH = "/ludovico";

    /**
     * zk地址
     */
    @Value("ludovico.service.zk.address")
    private static String ZK_HOST = "127.0.0.1:2181";

    /**
     * zk最大重试次数
     */
    @Value("ludovico.service.zk.retry.max")
    private static int MAX_RETRIES = 3;

    /**
     * zk的重试间隔时间会在这个baseSleepTime的基础上动态增加
     */
    @Value("ludovico.service.zk.baseSleepTime")
    private static int BASE_SLEEP_TIME = 1000;

    private static Set<String> nodeSet = ConcurrentHashMap.newKeySet();

    private static CuratorFramework zkclient;

    static {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        zkclient = CuratorFrameworkFactory.builder().connectString(ZK_HOST).retryPolicy(retryPolicy).build();
        zkclient.start();
    }



    public static void createPersistentNode(String path) {
        path = ZK_ROOT_PATH + path;
        try {
            if (nodeSet.contains(path) || zkclient.checkExists().forPath(path) != null) {
                log.warn("Node already exists! Path:{}", path);
                return;
            }
            zkclient.create().creatingParentsIfNeeded().forPath(path);
            nodeSet.add(path);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error when create zk node,exception:{}", e.toString());
        }
    }


    public static void removePersistentNode(String path) {
        path = ZK_ROOT_PATH + path;
        try {
            if (!nodeSet.contains(path) && zkclient.checkExists().forPath(path) == null) {
                log.warn("Node path:{} not exists!", path);
                return;
            }
            zkclient.delete().forPath(path);
        } catch (Exception e) {
            log.error("Error when delete zk node,exception:{}", e.toString());
        }
    }

    public static List<String> getChildNodes(String path) {
        path = ZK_ROOT_PATH + path;
        List<String> nodes = Lists.newArrayList();
        try {
            nodes = zkclient.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    /**
     * 监听子节点变动
     *
     * @param path
     */
    public static void registerChildrenWatcher(String path, Map<String, List<InetSocketAddress>> serviceCacheMap) {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkclient, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            List<String> addressList = curatorFramework.getChildren().forPath(path);
            List<InetSocketAddress> inetSocketAddressList = addressList.stream().map(AddressUtils::toInetSocketAddress).collect(Collectors.toList());
            serviceCacheMap.put(path, inetSocketAddressList);
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);

    }

    public static void shutdown() {
        try {
            nodeSet.clear();
            zkclient.delete().forPath(ZK_ROOT_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
