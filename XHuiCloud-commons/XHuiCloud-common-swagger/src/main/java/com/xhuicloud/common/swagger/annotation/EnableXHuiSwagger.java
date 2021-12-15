package com.xhuicloud.common.swagger.annotation;

import com.xhuicloud.common.swagger.config.SwaggerAutoConfiguration;
import com.xhuicloud.common.swagger.config.SwaggerConfiguration;
import com.xhuicloud.common.swagger.properties.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * @program: XHuiCloud
 * @description: EnableSwagger
 * @author: Sinda
 * @create: 2020-03-03 09:37
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerConfiguration.class, SwaggerAutoConfiguration.class})
public @interface EnableXHuiSwagger {
}
