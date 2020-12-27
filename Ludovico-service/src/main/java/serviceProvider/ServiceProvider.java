package serviceProvider;

/**
 * @author lizilin
 */
public interface ServiceProvider {

    Object getService(String serviceName);

    void publishService(String serviceName, Object service);

}
