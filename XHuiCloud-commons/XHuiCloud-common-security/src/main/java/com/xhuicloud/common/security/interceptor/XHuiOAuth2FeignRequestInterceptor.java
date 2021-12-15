package com.xhuicloud.common.security.interceptor;

import cn.hutool.core.collection.CollUtil;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.commons.security.AccessTokenContextRelay;
import org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import java.util.Collection;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;
import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

@Slf4j
public class XHuiOAuth2FeignRequestInterceptor extends OAuth2FeignRequestInterceptor {

    private final OAuth2ClientContext oAuth2ClientContext;

    private final AccessTokenContextRelay accessTokenContextRelay;

    public XHuiOAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource,
                                      AccessTokenContextRelay accessTokenContextRelay) {
        super(oAuth2ClientContext, resource);
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.accessTokenContextRelay = accessTokenContextRelay;
    }

    @Override
    public void apply(RequestTemplate template) {
        Collection<String> fromHeader = template.headers().get(FROM);
        if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(IS_COMMING_INNER_YES)) {
            return;
        }

        accessTokenContextRelay.copyToken();
        if (oAuth2ClientContext != null && oAuth2ClientContext.getAccessToken() != null) {
            super.apply(template);
        }
    }
}
