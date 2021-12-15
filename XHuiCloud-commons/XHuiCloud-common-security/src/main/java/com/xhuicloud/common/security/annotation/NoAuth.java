package com.xhuicloud.common.security.annotation;

import java.lang.annotation.*;

/**
 * @program: XHuiCloud
 * @description: NoAuth
 * @author: Sinda
 * @create: 2019-12-27 00:10
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuth {

    boolean value() default true;

}
