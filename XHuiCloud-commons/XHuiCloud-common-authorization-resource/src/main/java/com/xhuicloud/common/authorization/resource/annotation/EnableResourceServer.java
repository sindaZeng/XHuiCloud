package com.xhuicloud.common.authorization.resource.annotation;

import com.xhuicloud.common.authorization.resource.ResourceServerAutoConfiguration;
import com.xhuicloud.common.authorization.resource.config.ResourceServerConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ResourceServerAutoConfiguration.class, ResourceServerConfiguration.class })
public @interface EnableResourceServer {
}
