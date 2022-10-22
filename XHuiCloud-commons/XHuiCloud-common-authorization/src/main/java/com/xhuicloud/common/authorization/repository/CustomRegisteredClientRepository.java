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

package com.xhuicloud.common.authorization.repository;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.authorization.resource.properties.SecurityProperties;
import com.xhuicloud.common.core.constant.AuthorizationConstants;
import com.xhuicloud.upms.entity.SysClientDetails;
import com.xhuicloud.upms.feign.SysClientDetailFeign;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final SysClientDetailFeign sysClientDetailFeign;

    private final SecurityProperties securityProperties;

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findById(String id) {
        return findByClientId(id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        SysClientDetails sysClientDetails = sysClientDetailFeign.getById(clientId, AuthorizationConstants.FROM).getData();
        if (sysClientDetails == null) {
            return null;
        }
        RegisteredClient.Builder builder = RegisteredClient.withId(String.valueOf(sysClientDetails.getId()))
                .clientId(sysClientDetails.getClientId())
                .clientName(sysClientDetails.getName())
                .clientSecret(sysClientDetails.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .tokenSettings(getTokenSettings(sysClientDetails))
                .clientSettings(getClientSettings(sysClientDetails));

        // 设置授权模式
        Optional.ofNullable(sysClientDetails.getAuthorizedGrantTypes())
                .ifPresent(grants -> Arrays.asList(grants)
                        .forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));

        Optional.ofNullable(sysClientDetails.getWebServerRedirectUri()).ifPresent(builder::redirectUri);

        Optional.ofNullable(sysClientDetails.getScope()).ifPresent(builder::scope);

        return builder.build();
    }

    public TokenSettings getTokenSettings(SysClientDetails sysClientDetails) {
        return TokenSettings.builder()
                .accessTokenFormat(new OAuth2TokenFormat(
                        StrUtil.isNotBlank(sysClientDetails.getTokenFormat()) ?
                                sysClientDetails.getTokenFormat() : securityProperties.getAccessTokenFormat()))
                .accessTokenTimeToLive(
                        Duration.ofSeconds(Optional
                                .ofNullable(sysClientDetails.getAccessTokenValidity())
                                .orElse(securityProperties.getAuthorization().getAccessTokenValiditySeconds())))
                .refreshTokenTimeToLive(
                        Duration.ofSeconds(Optional
                                .ofNullable(sysClientDetails.getRefreshTokenValidity())
                                .orElse(securityProperties.getAuthorization().getRefreshTokenValiditySeconds())))
                .build();
    }

    public ClientSettings getClientSettings(SysClientDetails sysClientDetails) {
        return ClientSettings.builder()
                .setting("captchaEnable", sysClientDetails.getCaptchaEnable())
                .setting("multiLogin", sysClientDetails.getMultiLogin())
                .requireAuthorizationConsent(!BooleanUtil.toBoolean(sysClientDetails.getAutoApprove())).build();
    }
}
