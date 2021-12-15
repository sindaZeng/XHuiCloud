package com.xhuicloud.common.security.annotation;

import com.xhuicloud.common.security.component.ResourceServerAutoConfiguration;
import com.xhuicloud.common.security.component.XHuiSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * 资源服务器注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ResourceServerAutoConfiguration.class, XHuiSecurityBeanDefinitionRegistrar.class})
public @interface EnableXHuiResourceServer {

}
