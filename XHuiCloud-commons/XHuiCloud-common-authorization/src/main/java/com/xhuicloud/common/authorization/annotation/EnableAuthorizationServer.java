package com.xhuicloud.common.authorization.annotation;

import com.xhuicloud.common.authorization.AuthorizationServerAutoConfiguration;
import com.xhuicloud.common.authorization.resource.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableWebSecurity(debug = true)
@EnableConfigurationProperties(SecurityProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(AuthorizationServerAutoConfiguration.class)
public @interface EnableAuthorizationServer {
}
