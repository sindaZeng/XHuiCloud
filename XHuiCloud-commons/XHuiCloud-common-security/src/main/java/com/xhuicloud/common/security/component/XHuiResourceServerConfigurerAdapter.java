package com.xhuicloud.common.security.component;

import com.xhuicloud.common.security.component.PermitNoAuthUrlProperties;
import com.xhuicloud.common.security.component.ResourceAuthExceptionEntryPoint;
import com.xhuicloud.common.security.component.XHuiUserAuthenticationConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

/**
 * @program: XHuiCloud
 * @description: 资源服务器配置
 * @author: Sinda
 * @create: 2019-12-28 00:12
 **/
@Slf4j
public class XHuiResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Autowired
    protected RemoteTokenServices remoteTokenServices;

    @Autowired
    private RestTemplate lbRestTemplate;

    @Autowired
    private PermitNoAuthUrlProperties permitNoAuthUrlProperties;

    @Autowired
    protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new XHuiUserAuthenticationConverter());

        remoteTokenServices.setRestTemplate(lbRestTemplate);
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint).tokenExtractor(tokenExtractor)
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
        permitNoAuthUrlProperties.registry(registry);
        registry.anyRequest().authenticated()
                .and().csrf().disable();
    }

}
