package com.xhuicloud.common.authorization.constant;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 自定义GrantType
 */
public interface CustomAuthorizationGrantType {

    /**
     * 社交登录
     */
    AuthorizationGrantType SOCIAL = new AuthorizationGrantType("social");

}
