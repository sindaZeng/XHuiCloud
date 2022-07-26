package com.xhuicloud.common.authorization.extension.core;

import com.xhuicloud.common.authorization.resource.userdetails.XHuiUser;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.HashMap;
import java.util.Map;

public class CustomJWTOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {
        AbstractAuthenticationToken token = null;
        Authentication clientAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (clientAuthentication instanceof OAuth2ClientAuthenticationToken) {
            token = (OAuth2ClientAuthenticationToken) clientAuthentication;
        }
        if (ObjectUtils.isNotEmpty(token) && token.isAuthenticated()
                && OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            Authentication authentication = context.getPrincipal();
            if (ObjectUtils.isNotEmpty(authentication)) {
                if (authentication instanceof UsernamePasswordAuthenticationToken) {
                    XHuiUser principal = (XHuiUser) authentication.getPrincipal();
                    Map<String, Object> attributes = new HashMap();
                    attributes.put("tenantId", principal.getTenantId());
                    JwtClaimsSet.Builder jwtClaimSetBuilder = context.getClaims();
                    jwtClaimSetBuilder.claims(claims -> {
                        claims.putAll(attributes);
                    });
                }
            }
        }
    }
}
