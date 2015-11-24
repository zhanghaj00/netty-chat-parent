package io.ganguo.chat.core.protocol;

/**
 * @author Tony
 * @createAt Feb 17, 2015
 */
public class Handlers {
    /**
     * 路由转发
     */
    public static final short ROUTE = 0x1000;

    /**
     * 用户
     */
    public static final short USER = 0x0001;

    /**
     * 消息
     */
    public static final short MESSAGE = 0x0002;

    /**
     * 心跳
     */
    public static final short HEARTBEAT = 0x0003;

    /**
     * 异常处理
     */
    public static final short EXCEPTION = 0x0004;
}
