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

package com.xhuicloud.common.security.social;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.enums.login.LoginTypeEnum;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class SocialTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "social";

    private final AuthenticationManager authenticationManager;

    public SocialTokenGranter(AuthenticationManager authenticationManager,
                                                  AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                                  OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String loginType = parameters.get("type");
        if (loginType == null) {
            throw new InvalidGrantException("login type cannot be empty");
        }

        if (!Arrays.stream(LoginTypeEnum.values()).anyMatch(loginTypeEnum -> loginTypeEnum.getType().equals(loginType))) {
            throw new InvalidGrantException("login type not supported " + loginType);
        }

        String authCode = parameters.get("authCode");

        if (StrUtil.isBlank(authCode)) {
            throw new InvalidGrantException("authCode cannot be empty");
        }

        parameters.remove("authCode");

        Authentication userAuth = new SocialAuthenticationToken(authCode);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            // AuthenticationProvider中的supports来表明支持什么样的MobileCodeAuthenticationToken
            userAuth = authenticationManager.authenticate(userAuth);
        }
        catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        }
        catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user" );
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
