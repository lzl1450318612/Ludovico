package enums;

/**
 * @author lizilin
 */
public enum ExceptionEnum {


    /**
     * 解码异常
     */
    DECODE_EXCEPTION(100500,"Decode exception"),
    /**
     * 编码异常
     */
    ENCODE_EXCEPTION(100600, "Encode exception"),

    /**
     * 方法调用异常
     */
    INVOKE_EXCEPTION(100700, "Invoke exception");

    private int code;

    private String message;


    ExceptionEnum(int code, String message) {
    }
}
