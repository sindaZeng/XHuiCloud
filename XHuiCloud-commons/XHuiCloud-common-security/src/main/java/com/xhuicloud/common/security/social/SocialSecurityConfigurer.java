package com.xhuicloud.common.security.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.common.security.component.ResourceAuthExceptionEntryPoint;
import com.xhuicloud.common.security.service.XHuiUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: XHuiCloud
 * @description: SocialSecurityConfigurer
 * @author: Sinda
 * @create: 2020-06-16 16:41
 */

public class SocialSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationEventPublisher defaultAuthenticationEventPublisher;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private XHuiUserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) {
        SocialAuthenticationFilter socialAuthenticationFilter = new SocialAuthenticationFilter();
        socialAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        socialAuthenticationFilter.setEventPublisher(defaultAuthenticationEventPublisher);
        socialAuthenticationFilter.setAuthenticationEntryPoint(new ResourceAuthExceptionEntryPoint(objectMapper));

        SocialAuthenticationProvider socialAuthenticationProvider = new SocialAuthenticationProvider();
        socialAuthenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(socialAuthenticationProvider)
                .addFilterAfter(socialAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
