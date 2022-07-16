package com.xhuicloud.common.authorization.repository;

import cn.hutool.core.util.BooleanUtil;
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
import org.springframework.util.StringUtils;

import java.time.Duration;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        SysClientDetails sysClientDetails = sysClientDetailFeign.getById(clientId, AuthorizationConstants.FROM).getData();
        if (sysClientDetails == null) {
            // todo
        }
        RegisteredClient.Builder builder = RegisteredClient.withId(String.valueOf(sysClientDetails.getId()))
                .clientId(sysClientDetails.getClientId())
                .clientSecret("{noop}" + sysClientDetails.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .tokenSettings(getTokenSettings(sysClientDetails))
                .clientSettings(getClientSettings(sysClientDetails));

        // 设置授权模式
        Optional.ofNullable(sysClientDetails.getAuthorizedGrantTypes())
                .ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
                        .forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));

        Optional.ofNullable(sysClientDetails.getWebServerRedirectUri()).ifPresent(builder::redirectUri);

        Optional.ofNullable(sysClientDetails.getScope()).ifPresent(builder::scope);

        return builder.build();
    }

    public TokenSettings getTokenSettings(SysClientDetails sysClientDetails) {
        return TokenSettings.builder()
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
                .requireAuthorizationConsent(!BooleanUtil.toBoolean(sysClientDetails.getAutoapprove())).build();
    }
}
