package pojo;

import enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 调用方法的详细信息
 *
 * @author lizilin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvokeInfo implements Serializable {

    private static final long serialVersionUID = 219871498376110257L;

    /**
     * 请求ID
     */
    private Long id;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数名
     */
    private Object[] args;

    /**
     * 参数类型
     */
    private Class<?>[] argsTypes;

    /**
     * rpc消息类型
     */
    private MessageType messageType;

    public String getServiceName(){
        return methodName;
    }

}
