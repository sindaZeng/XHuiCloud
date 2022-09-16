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

package com.xhuicloud.common.authorization.extension.social;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.authorization.extension.core.OAuth2CustomAuthenticationProvider;
import com.xhuicloud.common.authorization.extension.core.UsernamePasswordGrantAuthenticationToken;
import com.xhuicloud.common.authorization.resource.constant.CustomAuthorizationGrantType;
import com.xhuicloud.common.authorization.resource.constant.LoginPlatformEnum;
import com.xhuicloud.common.authorization.utils.OAuth2EndpointUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Map;

public class OAuth2SocialAuthenticationProvider extends OAuth2CustomAuthenticationProvider<OAuth2SocialGrantAuthenticationToken, UsernamePasswordGrantAuthenticationToken> {

    private final static String LOGIN_PLATFORM = "platform";
    public OAuth2SocialAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authenticationManager, authorizationService, tokenGenerator);
    }

    @Override
    public AuthorizationGrantType getGrantType() {
        return CustomAuthorizationGrantType.SOCIAL;
    }

    @Override
    public UsernamePasswordGrantAuthenticationToken buildAuthenticationToken(Map<String, Object> additionalParameters) {
        // 登录平台
        String platform = (String) additionalParameters.get(LOGIN_PLATFORM);
        String code = (String) additionalParameters.get(OAuth2ParameterNames.CODE);
        if (StrUtil.isBlankOrUndefined(platform) || !LoginPlatformEnum.hasType(platform)) {
            OAuth2EndpointUtils.throwError(
                    "unsupported_platform",
                    LOGIN_PLATFORM,"");
        }
        if (StrUtil.isBlankOrUndefined(code)) {
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    OAuth2ParameterNames.CODE,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        return new UsernamePasswordGrantAuthenticationToken(code, null, platform);
    }

}
