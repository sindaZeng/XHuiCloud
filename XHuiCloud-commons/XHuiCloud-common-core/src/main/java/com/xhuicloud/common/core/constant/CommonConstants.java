/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.core.constant;

/**
 * @program: XHuiCloud
 * @description: 公共的内容
 * @author: Sinda
 * @create: 2020-01-01 19:26
 */
public interface CommonConstants {

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;

    /**
     * 失败标记
     */
    Integer FAIL = 1;

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
     * redis route reload 事件
     */
    String ROUTE_RELOAD = "gateway:route:reload";

    /**
     * redis client reload 事件
     */
    String CLIENT_RELOAD = "gateway:client:reload";

    /**
     * 网关路由加载
     */
    String GATEWAY_JVM_ROUTE_RELOAD= "gateway:jvm:route:reload";

    /**
     * 微信客户端重新加载
     */
    String WECHAT_CLIENT_RELOAD= "wechat:client:reload";

    /**
     * 租户ID
     */
    String TENANT_ID = "tenant_id";

    /**
     * 用户ID
     */
    String USER_ID = "userId";

    /**
     * 租户参数名称
     */
    String TENANT_NAME = "tenantName";

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



    /**
     * 用户名参数名称
     */
    String USER_USERNAME = "user_name";

    /**
     * 用户手机号参数名称
     */
    String USER_PHONE = "phone";

    /**
     * 回调
     */
    String REDIRECT_URL = "redirect_url";
}
