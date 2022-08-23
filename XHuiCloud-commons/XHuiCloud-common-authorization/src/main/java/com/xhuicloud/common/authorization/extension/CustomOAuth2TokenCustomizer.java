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

package com.xhuicloud.common.authorization.extension;

import com.xhuicloud.common.authorization.resource.userdetails.XHuiUser;
import com.xhuicloud.common.core.constant.CommonConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义token增强
 */
public class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer {

    @Override
    public void customize(OAuth2TokenContext context) {
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
                    attributes.put(CommonConstants.USER_ID, principal.getId());
                    attributes.put(CommonConstants.TENANT_NAME, principal.getTenantName());
                    attributes.put(CommonConstants.TENANT_ID, principal.getTenantId());
                    setAttributes(attributes, context);
                }
            }
        }
    }

    private void setAttributes(Map<String, Object> attributes, OAuth2TokenContext context) {
        if (context instanceof JwtEncodingContext) {
            JwtClaimsSet.Builder builder = ((JwtEncodingContext) context).getClaims();
            builder.claims(claims -> claims.putAll(attributes));
        }
        if (context instanceof OAuth2TokenClaimsContext) {
            OAuth2TokenClaimsSet.Builder builder = ((OAuth2TokenClaimsContext) context).getClaims();
            builder.claims(claims -> claims.putAll(attributes));
        }
    }

}
