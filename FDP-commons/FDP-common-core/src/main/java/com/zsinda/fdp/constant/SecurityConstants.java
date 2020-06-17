package com.zsinda.fdp.constant;

/**
 * @program: FDPlatform
 * @description: SecurityConstants
 * @author: Sinda
 * @create: 2020-06-17 09:59
 */
public interface SecurityConstants {

    /**
     * 验证码前缀
     */
    String CODE_KEY = "FDP_SMS_CODE_KEY";

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
}
