package org.larry.mq;

/**
 * mq基本信息连接
 */
public class ActiveMQConnection {

    /**
     * 用户连接名
     */
    public final static String DEFAULT_USER = "admin" ;

    /**
     * 登录密码
     */
    public static final String DEFAULT_PASSWORD = "admin";

    /**
     * url连接 mq tcp 默认端口号为61616可以配置
     */
    public static final String DEFAULT_BROKER_URL = "tcp://192.168.130.130:61616" ;
}
