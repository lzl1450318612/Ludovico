package utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author lizilin
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        SpringContextUtils.context = context;
    }

}
