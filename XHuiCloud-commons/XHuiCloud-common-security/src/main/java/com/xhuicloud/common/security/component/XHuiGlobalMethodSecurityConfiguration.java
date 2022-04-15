package com.xhuicloud.common.security.component;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configurable
public class XHuiGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Override
    public MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }

}
