package com.zsinda.fdp.config;

import com.zsinda.fdp.component.PermitHavaInnerUrlProperties;
import com.zsinda.fdp.component.ResourceAuthExceptionEntryPoint;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

/**
 * @program: FDPlatform
 * @description: 资源服务器配置
 * @author: Sinda
 * @create: 2019-12-28 00:12
 **/
@Slf4j
public class FdpResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Autowired
    protected RemoteTokenServices remoteTokenServices;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestTemplate lbRestTemplate;

    @Autowired
    private PermitHavaInnerUrlProperties permitHavaInnerUrlProperties;

    @Autowired
    protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
        userTokenConverter.setUserDetailsService(userDetailsService);
        accessTokenConverter.setUserTokenConverter(userTokenConverter);

        remoteTokenServices.setRestTemplate(lbRestTemplate);
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        resources.
                authenticationEntryPoint(resourceAuthExceptionEntryPoint)
                .tokenServices(remoteTokenServices);
    }



    /**
     * 安全配置，对外暴露放行url
     *
     * @param httpSecurity
     */
    @Override
    @SneakyThrows
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();
        permitHavaInnerUrlProperties.getIgnoreUrls()
                .forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated()
                .and().csrf().disable();
    }

}
