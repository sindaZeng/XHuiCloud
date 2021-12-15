package com.xhuicloud.common.xxl.annotation;

import com.xhuicloud.common.xxl.XHuiXxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ XHuiXxlJobAutoConfiguration.class })
public @interface EnableXxlJob {
}
