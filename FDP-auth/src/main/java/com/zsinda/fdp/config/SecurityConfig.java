package com.zsinda.fdp.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: FDPlatform
 * @description: SecurityConfig
 * @author: Sinda
 * @create: 2019-12-25 23:49
 **/

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    /**
     * 不拦截资源
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**","/oauth/check_token");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()//表单登录
                .loginPage("/token/toLogin")
                .loginProcessingUrl("/token/form")
                .and()
                .authorizeRequests()//对请求授权
                .antMatchers("/token/**","/mobile/**").permitAll() //匹配这个url 放行
                .anyRequest().authenticated()//任何请求都要授权
        .and().csrf().disable();//跨站请求伪造攻击
    }
}
