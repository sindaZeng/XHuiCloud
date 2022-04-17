package com.xhuicloud.common.security.component;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods;

public class XHuiOAuth2MethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    public XHuiOAuth2MethodSecurityExpressionHandler(ApplicationContext applicationContext) {
        setApplicationContext(applicationContext);
        setExpressionParser(new XHuiOAuth2ExpressionParser(getExpressionParser()));
    }

    @Override
    public StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, MethodInvocation mi) {
        StandardEvaluationContext ec = super.createEvaluationContextInternal(authentication, mi);
        ec.setVariable("oauth2", new OAuth2SecurityExpressionMethods(authentication));
        return ec;
    }
}
