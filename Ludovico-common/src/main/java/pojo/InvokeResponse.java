package pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Rpc请求响应实体
 */
@Data
@Builder
public class InvokeResponse {

    private Long id;

    private int code;

    private Object data;

    private String message;

}
