package com.xhuicloud.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.auth.service.XHuiClientDetailsServiceImpl;
import com.xhuicloud.common.security.component.ResourceAuthExceptionEntryPoint;
import com.xhuicloud.common.security.component.XHuiWebResponseExceptionTranslator;
import com.xhuicloud.common.security.service.XHuiUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @program: XHuiCloud
 * @description: AuthorizationServerConfig
 * @author: Sinda
 * @create: 2019-12-27 22:26
 **/
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class XHuiAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final XHuiClientDetailsServiceImpl xHuiClientDetailsServiceImpl;

    private final XHuiUserDetailsService XHuiUserDetailsService;

    private final AuthorizationCodeServices xHuiAuthCodeServicesImpl;

    private final TokenEnhancer xhuiTokenEnhancer;

    private final ObjectMapper objectMapper;

    private final TokenStore xHuiRedisTokenStore;

    /**
     * 配置客户端信息
     *
     * @param clientDetailsServiceConfigurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        clientDetailsServiceConfigurer.withClientDetails(xHuiClientDetailsServiceImpl);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()
                .authenticationEntryPoint(new ResourceAuthExceptionEntryPoint(objectMapper))
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置有哪些用户可以来访问认证服务器
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(xHuiRedisTokenStore)
                .tokenEnhancer(xhuiTokenEnhancer)
                .userDetailsService(XHuiUserDetailsService)
                .authorizationCodeServices(xHuiAuthCodeServicesImpl)
                .authenticationManager(authenticationManager)//校验用户信息是否合法
                .reuseRefreshTokens(false)
                .pathMapping("/oauth/confirm_access", "/token/confirm_access")//设置成自己的授权页面
                .exceptionTranslator(new XHuiWebResponseExceptionTranslator()); //修改Oauth2定义的错误信息 为我们定义的错误信息
    }
}
