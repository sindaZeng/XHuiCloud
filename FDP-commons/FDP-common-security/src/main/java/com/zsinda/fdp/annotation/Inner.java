package com.zsinda.fdp.annotation;

import java.lang.annotation.*;

/**
 * @program: FDPlatform
 * @description: Inner
 * @author: Sinda
 * @create: 2019-12-27 00:10
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {

    /**
     * 是否AOP统一处理
     *
     * @return false, true
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段(预留)
     *
     * @return {}
     */
    String[] field() default {};
}
