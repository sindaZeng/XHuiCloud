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

package com.xhuicloud.common.authorization;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xhuicloud.common.authorization.extension.CustomOAuth2TokenCustomizer;
import com.xhuicloud.common.authorization.extension.core.CustomDaoAuthenticationProvider;
import com.xhuicloud.common.authorization.extension.password.OAuth2PasswordAuthenticationConverter;
import com.xhuicloud.common.authorization.extension.password.OAuth2PasswordAuthenticationProvider;
import com.xhuicloud.common.authorization.extension.sms.OAuth2SmsAuthenticationConverter;
import com.xhuicloud.common.authorization.extension.sms.OAuth2SmsAuthenticationProvider;
import com.xhuicloud.common.authorization.handler.AuthenticationErrorResponseHandler;
import com.xhuicloud.common.authorization.handler.DelegatingAuthenticationFailureHandler;
import com.xhuicloud.common.authorization.handler.DelegatingAuthenticationSuccessHandler;
import com.xhuicloud.common.authorization.jose.Jwks;
import com.xhuicloud.common.authorization.resource.properties.SecurityProperties;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
@Import({AuthorizationServerAutoConfiguration.JwtConfiguration.class,
        AuthorizationServerAutoConfiguration.OpaqueTokenConfiguration.class})
public class AuthorizationServerAutoConfiguration {

    private final SecurityProperties securityProperties;

    private final DelegatingAuthenticationSuccessHandler delegatingAuthenticationSuccessHandler;

    private final DelegatingAuthenticationFailureHandler delegatingAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return SecurityHolder.passwordEncoder();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();
        http.apply(authorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> {        // 令牌端点
                    tokenEndpoint.accessTokenRequestConverter(authenticationConverter()) // 自定义Converter
                            .accessTokenResponseHandler(delegatingAuthenticationSuccessHandler) // 登录成功委托
                            // 登录请求失败委托 只在抛出异常类型为: OAuth2AuthenticationException执行, 需要转换异常，否则会进入AuthenticationEntryPoint 处理
                            .errorResponseHandler(delegatingAuthenticationFailureHandler);
                }).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // 客户端认证端点
                        oAuth2ClientAuthenticationConfigurer.errorResponseHandler(delegatingAuthenticationFailureHandler)) // 客户端认证失败委托
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint    // 授权端点
                        .errorResponseHandler(new AuthenticationErrorResponseHandler(securityProperties.getAuthorization().getErrorPage()))  // 授权异常处理
                        .consentPage(securityProperties.getAuthorization().getConsentPage()))); // 授权码端点个性化confirm页面

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        DefaultSecurityFilterChain securityFilterChain = http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer)
                .and()
                .apply(new FormLoginConfigurer()) // 自定义授权登录页
                .and()
                .build();

        // 拓展授权模式
        addCustomOAuth2AuthenticationProvider(http);
        return securityFilterChain;
    }

    private void addCustomOAuth2AuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        OAuth2TokenGenerator tokenGenerator = http.getSharedObject(OAuth2TokenGenerator.class);
        OAuth2PasswordAuthenticationProvider passwordAuthenticationProvider = new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator);
        OAuth2SmsAuthenticationProvider smsAuthenticationProvider = new OAuth2SmsAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator);
        http.authenticationProvider(new CustomDaoAuthenticationProvider());
        http.authenticationProvider(passwordAuthenticationProvider);
        http.authenticationProvider(smsAuthenticationProvider);
    }

    private AuthenticationConverter authenticationConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new OAuth2PasswordAuthenticationConverter(),
                new OAuth2SmsAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer(securityProperties.getAuthorization().getIssuer()).build();
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = "xhuicloud.security.access-token-format", havingValue = "self-contained")
    static class JwtConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public JWKSource<SecurityContext> jwkSource() {
            RSAKey rsaKey = Jwks.generateRsa();
            return (jwkSelector, securityContext) -> jwkSelector.select(new JWKSet(rsaKey));
        }

        @Bean
        @ConditionalOnMissingBean
        public OAuth2TokenCustomizer<JwtEncodingContext> accessTokenCustomizer() {
            return new CustomOAuth2TokenCustomizer();
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = "xhuicloud.security.access-token-format", havingValue = "reference")
    static class OpaqueTokenConfiguration {

        /**
         * 匿名令牌增强
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer() {
            return new CustomOAuth2TokenCustomizer();
        }

    }

}
