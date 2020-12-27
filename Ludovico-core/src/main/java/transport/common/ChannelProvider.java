package transport.common;

import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizilin
 * @date 2020/10/23 7:28 下午
 */
@Service
public class ChannelProvider {

    Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        String address = inetSocketAddress.toString();
        if (channelMap.containsKey(address)) {
            Channel channel = channelMap.get(address);
            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(address);
            }

        }
        return null;
        // 如果没有找到channel或者channel不可用，则重连获取channel
//        channelMap.put();
//        return;

    }

}
