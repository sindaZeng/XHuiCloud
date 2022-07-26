package com.xhuicloud.common.authorization.resource.annotation;

import com.xhuicloud.common.authorization.resource.ResourceServerAutoConfiguration;
import com.xhuicloud.common.authorization.resource.properties.PermitAnonymousUrlProperties;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({PermitAnonymousUrlProperties.class, SecurityProperties.class})
@Import(ResourceServerAutoConfiguration.class)
public @interface EnableResourceServer {
}
