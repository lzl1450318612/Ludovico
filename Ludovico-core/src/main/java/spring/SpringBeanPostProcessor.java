package spring;

import annotations.Piano;
import annotations.Play;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import proxy.ClientProxy;
import serviceDiscovery.ServiceDiscovery;
import serviceProvider.ServiceProvider;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author lizilin
 */
@Slf4j
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private ServiceDiscovery serviceDiscovery;

    @Resource
    private ServiceProvider serviceProvider;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Piano.class)) {
            serviceProvider.publishService(bean.getClass().getCanonicalName(), bean);
            log.info("service publish!service name:{}", bean.getClass().getCanonicalName());
        }
        return bean;
    }

    @Override
    @SneakyThrows
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Play play = field.getAnnotation(Play.class);
            if (play != null) {

                Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ClientProxy());
                field.set(bean, proxy);
            }
        }
        return bean;
    }
}
