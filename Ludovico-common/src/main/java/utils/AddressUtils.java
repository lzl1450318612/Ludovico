package utils;

import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

/**
 * @author lizilin
 */
public class AddressUtils {

    public static InetSocketAddress toInetSocketAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        String[] arr = address.split(":");
        return new InetSocketAddress(arr[0], Integer.parseInt(arr[1]));
    }
}
