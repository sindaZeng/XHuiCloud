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

package com.xhuicloud.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.auth.service.XHuiClientDetailsServiceImpl;
import com.xhuicloud.common.security.component.ResourceAuthExceptionEntryPoint;
import com.xhuicloud.common.security.component.XHuiWebResponseExceptionTranslator;
import com.xhuicloud.common.security.service.XHuiUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
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
                .pathMapping("/oauth/confirm_access", "/token/confirm_access");//设置成自己的授权页面
    }
}
