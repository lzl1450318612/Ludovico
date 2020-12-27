package enums;

/**
 * RPC消息类型
 *
 * @author lizilin
 */

public enum MessageType {

    /**
     * 心跳包
     */
    HEAT_BEAT(0, "心跳包"),
    /**
     * 普通包
     */
    NORMAL(1, "普通包");

    private int code;

    private String description;

    MessageType(int code, String description) {
    }
}
