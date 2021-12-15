package com.xhuicloud.common.core.constant;

/**
 * @program: XHuiCloud
 * @description: SecurityConstants
 * @author: Sinda
 * @create: 2020-06-17 09:59
 */
public interface SecurityConstants {

    /**
     * 验证码前缀
     */
    String CODE_KEY = "XHUI_SMS_CODE_KEY";

    /**
     * 验证码有效期
     */
    Long CODE_TIME = 60L;

    /**
     * OAUTH
     */
    String OAUTH_TOKEN = "/oauth/token";

    /**
     * 第三方社交登录
     */
    String TOKEN_SOCIAL = "/token/social";

    /**
     * 刷新TOKEN
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 用户信息
     */
    String USER_INFO = "user_info";

    /**
     * 前缀
     */
    String XHUI_PREFIX = "xhui_";

    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";

    /**
     * oauth 相关前缀
     */
    String OAUTH_CODE_PREFIX = "oauth:code:";

}
