package com.zsinda.fdp.config;

import com.zsinda.fdp.component.FdpWebResponseExceptionTranslator;
import com.zsinda.fdp.service.FdpUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @program: FDPlatform
 * @description: AuthorizationServerConfig
 * @author: Sinda
 * @create: 2019-12-27 22:26
 **/
@Configuration
@EnableAuthorizationServer
public class FdpAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private FdpUserDetailsService fdpUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private TokenEnhancer fdpTokenEnhancer;

    /**
     *  配置客户端信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        return tokenStore;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }


    /**
     * 配置有哪些用户可以来访问认证服务器
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore())
                .tokenEnhancer(fdpTokenEnhancer)
                .userDetailsService(fdpUserDetailsService)
                .authenticationManager(authenticationManager)//校验用户信息是否合法
                .reuseRefreshTokens(false)
                .pathMapping("/oauth/confirm_access", "/confirm_access")//设置成自己的授权页面
                .exceptionTranslator(new FdpWebResponseExceptionTranslator()); //修改Oauth2定义的错误信息 为我们定义的错误信息
    }
}
