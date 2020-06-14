package com.zsinda.fdp.annotation;

import com.zsinda.fdp.config.SwaggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @program: FDPlatform
 * @description: EnableSwagger
 * @author: Sinda
 * @create: 2020-03-03 09:37
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerConfiguration.class})
public @interface EnableFdpSwagger {
}
