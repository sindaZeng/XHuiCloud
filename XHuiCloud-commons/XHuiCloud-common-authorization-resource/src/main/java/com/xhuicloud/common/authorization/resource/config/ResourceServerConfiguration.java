package com.xhuicloud.common.authorization.resource.config;

import cn.hutool.core.util.ArrayUtil;
import com.xhuicloud.common.authorization.resource.properties.PermitAnonymousUrlProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
public class ResourceServerConfiguration {

    private final PermitAnonymousUrlProperties urlProperties;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/client1/test").permitAll()
                        .antMatchers(ArrayUtil.toArray(urlProperties.getIgnoreUrls(), String.class)).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(configurer -> configurer.jwt()
                        .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter()))
                .headers().frameOptions().disable().and().csrf().disable();
        return http.build();
    }

}
