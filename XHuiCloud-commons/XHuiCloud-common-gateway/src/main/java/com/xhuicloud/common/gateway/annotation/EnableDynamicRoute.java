package com.xhuicloud.common.gateway.annotation;

import com.xhuicloud.common.gateway.configuration.DynamicRouteAutoConfiguration;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DynamicRouteAutoConfiguration.class)
public @interface EnableDynamicRoute {
}
