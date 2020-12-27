package annotations;

import java.lang.annotation.*;

/**
 * 服务提供注解（提供一台piano供服务调用方去play）
 *
 * @author lizilin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Piano {
}
