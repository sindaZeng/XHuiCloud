package com.xhuicloud.common.authorization;

import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthorizationServerAutoConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return SecurityHolder.passwordEncoder();
    }

}
