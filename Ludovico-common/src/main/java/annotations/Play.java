package annotations;

import java.lang.annotation.*;

/**
 * play服务提供方提供的piano
 *
 * @author lizilin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface Play {
}
