package com.zsinda.fdp.constant;

/**
 * @program: FDPlatform
 * @description: 公共的内容
 * @author: Sinda
 * @create: 2020-01-01 19:26
 */
public interface CommonConstants {

    /**
     * 请求成功标记
     */
    Integer REQUEST_SUCCESS = 0;

    /**
     * 请求失败标记
     */
    Integer REQUEST_FAIL = 1;

    /**
     * 操作类型
     */
    String LOG_OPT_TYPE = "0";

    /**
     * 异常类型
     */
    String LOG_EX_TYPE = "1";

    /**
     * 路由key
     */
    String ROUTE_KEY = "gateway_route_key";

    /**
     * redis reload 事件
     */
    String ROUTE_REDIS_RELOAD_TOPIC = "gateway_redis_route_reload_topic";

    /**
     * 内存reload 时间
     */
    String ROUTE_JVM_RELOAD_TOPIC = "gateway_jvm_route_reload_topic";

    /**
     * 请求头 中租户ID
     */
    String TENANT_ID = "tenant_id";

    /**
     * 默认租户ID
     */
    Integer DEFAULT_TENANT_ID = 1;
}
