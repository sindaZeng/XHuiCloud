package com.xhuicloud.common.security.annotation;

import com.xhuicloud.common.security.config.ResourceServerAutoConfiguration;
import com.xhuicloud.common.security.config.XHuiSecurityBeanDefinitionRegistrar;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ResourceServerAutoConfiguration.class, XHuiSecurityBeanDefinitionRegistrar.class})
public @interface EnableXHuiResourceServer {

}
