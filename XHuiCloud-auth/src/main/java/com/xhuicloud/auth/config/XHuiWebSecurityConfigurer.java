package com.xhuicloud.auth.config;

import com.xhuicloud.common.security.handle.FormAuthFailureHandler;
import com.xhuicloud.common.security.handle.MobileAuthSuccessHandler;
import com.xhuicloud.common.security.handle.XHuiLogoutSuccessHandler;
import com.xhuicloud.common.security.handle.XHuiSimpleUrlAuthenticationSuccessHandler;
import com.xhuicloud.common.security.social.SocialSecurityConfigurer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//表单登录
                .loginPage("/token/login")
                .loginProcessingUrl("/token/form")
                .successHandler(xHuiSimpleUrlAuthenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(xHuiLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests()//对请求授权
                .antMatchers("/token/**", "/mobile/**", "/actuator/**")
                .permitAll() //匹配这个url 放行
                .anyRequest().authenticated()//任何请求都要授权
                .and().csrf().disable()//跨站请求伪造攻击
                .apply(socialSecurityConfigurer());
    }

    /**
     * 不拦截静态资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/error");
    }

    @Bean
    public SocialSecurityConfigurer socialSecurityConfigurer() {
        return new SocialSecurityConfigurer();
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
}
