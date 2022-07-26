package com.xhuicloud.common.authorization.resource;

import cn.hutool.core.util.ArrayUtil;
import com.xhuicloud.common.authorization.resource.config.CustomJwtAuthenticationConverter;
import com.xhuicloud.common.authorization.resource.properties.PermitAnonymousUrlProperties;
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUserDetailsService;
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUserDetailsServiceImpl;
import com.xhuicloud.upms.feign.SysUserServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
public class ResourceServerAutoConfiguration {

    private final PermitAnonymousUrlProperties urlProperties;

    private final XHuiUserDetailsService xHuiUserDetailsService;

    public CustomJwtAuthenticationConverter authenticationConverter() {
        CustomJwtAuthenticationConverter customJwtAuthenticationConverter = new CustomJwtAuthenticationConverter(xHuiUserDetailsService);
        return customJwtAuthenticationConverter;
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(ArrayUtil.toArray(urlProperties.getIgnoreUrls(), String.class)).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(configurer -> configurer.jwt()
                        .jwtAuthenticationConverter(authenticationConverter()))
                .headers().frameOptions().disable().and().csrf().disable();
        return http.build();
    }

}
