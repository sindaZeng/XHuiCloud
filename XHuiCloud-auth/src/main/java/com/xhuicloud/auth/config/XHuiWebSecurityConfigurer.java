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

import com.xhuicloud.common.security.handle.FormAuthFailureHandler;
import com.xhuicloud.common.security.handle.MobileAuthSuccessHandler;
import com.xhuicloud.common.security.handle.XHuiLogoutSuccessHandler;
import com.xhuicloud.common.security.handle.XHuiSimpleUrlAuthenticationSuccessHandler;
import com.xhuicloud.common.security.social.SocialAuthenticationProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @program: XHuiCloud
 * @description: SecurityConfig
 * @author: Sinda
 * @create: 2019-12-25 23:49
 **/
@Primary
@Order(80)
@Configuration
public class XHuiWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new FormAuthFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Bean
    public MobileAuthSuccessHandler mobileAuthSuccessHandler() {
        return new MobileAuthSuccessHandler();
    }

    @Bean
    public AuthenticationSuccessHandler xHuiSimpleUrlAuthenticationSuccessHandler() {
        return new XHuiSimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler xHuiLogoutSuccessHandler() {
        return new XHuiLogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(new SocialAuthenticationProvider())
                .formLogin()//表单登录
                .loginPage("/oauth2/login")
                .loginProcessingUrl("/oauth2/form")
                .successHandler(xHuiSimpleUrlAuthenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(xHuiLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests()//对请求授权
                .antMatchers("/oauth2/**", "/mobile/**", "/actuator/**")
                .permitAll() //匹配这个url 放行
                .anyRequest().authenticated()//任何请求都要授权
                .and().csrf().disable();//跨站请求伪造攻击
    }

    /**
     * 不拦截静态资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/error");
    }

}
