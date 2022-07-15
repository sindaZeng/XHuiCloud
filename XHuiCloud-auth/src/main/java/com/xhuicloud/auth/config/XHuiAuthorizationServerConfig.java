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

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xhuicloud.common.authorization.extension.password.OAuth2PasswordAuthenticationConverter;
import com.xhuicloud.common.authorization.extension.password.OAuth2PasswordAuthenticationProvider;
import com.xhuicloud.common.authorization.jose.Jwks;
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUser;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: XHuiCloud
 * @description: AuthorizationServerConfig
 * @author: Sinda
 * @create: 2019-12-27 22:26
 **/
@Configuration
@AllArgsConstructor
public class XHuiAuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

        http.apply(authorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> {// 个性化认证授权端点
                    tokenEndpoint.accessTokenRequestConverter(authenticationConverter()); // 注入自定义的授权认证Converter
                }).clientAuthentication(Customizer.withDefaults())
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint// 授权码端点个性化confirm页面
                        .consentPage("confirm_access")));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        DefaultSecurityFilterChain securityFilterChain = http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer)
                .and().build();
        addCustomOAuth2AuthenticationProvider(http);
        return securityFilterChain;
    }

    private void addCustomOAuth2AuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        JwtGenerator jwtGenerator = http.getSharedObject(JwtGenerator.class);
        jwtGenerator.setJwtCustomizer(accessTokenCustomizer());
        DelegatingOAuth2TokenGenerator tokenGenerator = new DelegatingOAuth2TokenGenerator(jwtGenerator, new OAuth2RefreshTokenGenerator());
        OAuth2PasswordAuthenticationProvider passwordAuthenticationProvider = new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator);

        http.authenticationProvider(passwordAuthenticationProvider);
    }

    private AuthenticationConverter authenticationConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new OAuth2PasswordAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    public OAuth2TokenCustomizer<JwtEncodingContext> accessTokenCustomizer() {
        return context -> {
            AbstractAuthenticationToken token = null;
            Authentication clientAuthentication = SecurityContextHolder.getContext().getAuthentication();
            if (clientAuthentication instanceof OAuth2ClientAuthenticationToken) {
                token = (OAuth2ClientAuthenticationToken) clientAuthentication;
            }

            if (ObjectUtils.isNotEmpty(token) && token.isAuthenticated() && OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                Authentication authentication = context.getPrincipal();
                if (ObjectUtils.isNotEmpty(authentication)) {
                    Set<String> authorities;
                    Set<String> authorizedScopes;
                    if (authentication instanceof UsernamePasswordAuthenticationToken) {
                        XHuiUser principal = (XHuiUser) authentication.getPrincipal();
                        Integer userId = principal.getId();
                        authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                        authorizedScopes = context.getAuthorizedScopes();
                        Map<String, Object> attributes = new HashMap();
                        attributes.put("openid", userId);
                        attributes.put("authorities", authorities);
                        attributes.put("scope", authorizedScopes);

                        JwtClaimsSet.Builder jwtClaimSetBuilder = context.getClaims();
                        jwtClaimSetBuilder.claims((claims) -> {
                            claims.putAll(attributes);
                        });
                    }

                    if (authentication instanceof OAuth2ClientAuthenticationToken) {
                        OAuth2ClientAuthenticationToken clientAuthenticationToken = (OAuth2ClientAuthenticationToken) authentication;
                        Map<String, Object> attributes = new HashMap();
                        if (CollectionUtils.isNotEmpty(clientAuthenticationToken.getAuthorities())) {
                            authorities = (Set) clientAuthenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                            attributes.put("authorities", authorities);
                        }

                        authorizedScopes = context.getAuthorizedScopes();
                        if (CollectionUtils.isNotEmpty(authorizedScopes)) {
                            attributes.put("scope", authorizedScopes);
                        }

                        JwtClaimsSet.Builder jwtClaimSetBuilder = context.getClaims();
                        jwtClaimSetBuilder.claims((claims) -> {
                            claims.putAll(attributes);
                        });
                    }
                }
            }

        };
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer("http://localhost:16000").build();
    }
}
