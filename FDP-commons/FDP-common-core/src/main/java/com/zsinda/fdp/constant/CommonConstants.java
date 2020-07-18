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
    String ROUTE_KEY = "gateway:route:key";

    /**
     * redis reload 事件
     */
    String ROUTE_RELOAD = "gateway:route:reload";

    /**
     * 内存reload 时间
     */
    String ROUTE_RELOAD_TIME = "gateway:route:reload:time";

    /**
     * 请求头 中租户ID
     */
    String TENANT_ID = "tenant_id";

    /**
     * 请求头 中租户ID
     */
    String VERSION = "version";

    /**
     * 默认租户ID
     */
    Integer DEFAULT_TENANT_ID = 1;

    /**
     * 支付宝 ua
     */
    String ALIPAY = "Alipay";

    /**
     * 微信 ua
     */
    String MICRO_MESSENGER = "MicroMessenger";

    /**
     * 该笔订单允许的最晚付款时间，
     * 逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（
     * 1c-当天的情况下，无论交易何时创建，都在0点关闭）。
     * 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     */
    String TIMEOUT_EXPRESS = "3m";

    /**
     * 扫码付款
     */
    String SCAN_CODE_PAY = "扫码付款";

}
