package com.xhuicloud.common.security.token;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.utils.KeyStrResolver;
import com.xhuicloud.common.security.component.XHuiRedisTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class XHuiTokenStoreAutoConfiguration {

    private final KeyStrResolver resolver;

    private final RedisConnectionFactory connectionFactory;

    @Bean
    public TokenStore tokenStore() {
        XHuiRedisTokenStore tokenStore = new XHuiRedisTokenStore(connectionFactory, resolver);
        tokenStore.setPrefix(SecurityConstants.XHUI_PREFIX + SecurityConstants.OAUTH_PREFIX);
        tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                return resolver.extract(super.extractKey(authentication), StrUtil.COLON);
            }
        });
        return tokenStore;
    }

}
