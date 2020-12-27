package utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lizilin
 */
public class SingletonUtils {

    private static final Map<String, Object> OBJECT_MAP = new HashMap<>();

    public static <T> T getInstance(Class<T> c) {
        String key = c.toString();
        Object instance = OBJECT_MAP.get(key);
        synchronized (c) {
            if (instance == null) {
                try {
                    instance = c.getDeclaredConstructor().newInstance();
                    OBJECT_MAP.put(key, instance);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return c.cast(instance);
    }
}
