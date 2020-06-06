package com.zsinda.fdp.component;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: FDPlatform
 * @description: SecurityConfig
 * @author: Sinda
 * @create: 2019-12-25 23:49
 **/
@Order
@Configuration
@AllArgsConstructor
public class FdpCommonWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 不拦截资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/oauth/check_token");
    }

}
