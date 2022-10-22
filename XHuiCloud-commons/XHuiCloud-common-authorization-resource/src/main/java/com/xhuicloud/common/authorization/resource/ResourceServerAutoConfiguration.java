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

package com.xhuicloud.common.authorization.resource;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.authorization.resource.component.CustomAuthenticationEntryPoint;
import com.xhuicloud.common.authorization.resource.component.CustomJwtAuthenticationConverter;
import com.xhuicloud.common.authorization.resource.component.CustomOpaqueTokenIntrospect;
import com.xhuicloud.common.authorization.resource.properties.SecurityProperties;
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
@EnableConfigurationProperties
public class ResourceServerAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final SecurityProperties securityProperties;

    private final XHuiUserDetailsService xHuiUserDetailsService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(ArrayUtil.toArray(securityProperties.getResourceServer().getIgnoreUrls(), String.class)).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(configurer -> {
//                        configurer
//                                .jwt()
//                                .jwtAuthenticationConverter(authenticationConverter());
                    configurer
                            .opaqueToken()
                            .introspector(opaqueTokenIntrospector());
                    configurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint()); // 异常处理
                })
                .headers().frameOptions().disable().and().csrf().disable();
        return http.build();
    }

    @Bean
    @Primary
    @LoadBalanced
    public RestTemplate lbRestTemplate() {
        // 获取上下文配置的ClientHttpRequestInterceptor 实现
        Map<String, ClientHttpRequestInterceptor> beansOfType = applicationContext
                .getBeansOfType(ClientHttpRequestInterceptor.class);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(new ArrayList<>(beansOfType.values()));
        return restTemplate;
    }

    public CustomJwtAuthenticationConverter authenticationConverter() {
        return new CustomJwtAuthenticationConverter(xHuiUserDetailsService);
    }

    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        SecurityProperties.ResourceServer resourceServer = securityProperties.getResourceServer();
        if (StrUtil.hasBlank(resourceServer.getIntrospectionUri(),
                resourceServer.getClientId(),
                resourceServer.getClientSecret())) {
            throw new OAuth2IntrospectionException("匿名令牌模式下: clientId/clientSecret/introspectionUri 均不能为空!");
        }
        JwtDecoder jwtDecoder = this.applicationContext.getBean(JwtDecoder.class);
        return new CustomOpaqueTokenIntrospect(
                resourceServer.getIntrospectionUri(),
                resourceServer.getClientId(),
                resourceServer.getClientSecret(),
                xHuiUserDetailsService,
                jwtDecoder);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
