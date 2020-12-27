package handler;

import enums.ExceptionEnum;
import exceptions.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pojo.InvokeInfo;
import serviceProvider.ServiceProvider;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lizilin
 * @date 2020/10/26 4:40 下午
 */
@Slf4j
@Component
public class InvokeHandler {

    @Resource
    private ServiceProvider serviceProvider;

    public Object handle(InvokeInfo invokeInfo) {
        Object service = serviceProvider.getService(invokeInfo.getServiceName());
        return invokeTargetMethod(invokeInfo, service);
    }

    private Object invokeTargetMethod(InvokeInfo invokeInfo, Object service) {
        Object result = null;
        try {
            Method method = service.getClass().getMethod(invokeInfo.getMethodName());
            result = method.invoke(service, invokeInfo.getArgs());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("方法调用异常！调用方法名：{}", invokeInfo.getServiceName() + "." + invokeInfo.getMethodName());
            throw new ServerException(ExceptionEnum.INVOKE_EXCEPTION);
        }
        return result;
    }

}
