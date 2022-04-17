package com.xhuicloud.common.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

public class XHuiGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Autowired
    public ApplicationContext applicationContext;

    @Override
    public MethodSecurityExpressionHandler createExpressionHandler() {
        return new XHuiOAuth2MethodSecurityExpressionHandler(applicationContext);
    }

}
