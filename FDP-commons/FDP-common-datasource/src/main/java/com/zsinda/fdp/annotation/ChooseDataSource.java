package com.zsinda.fdp.annotation;

import java.lang.annotation.*;

/**
 * @program: FDPlatform
 * @description: 目标数据源注解，注解在方法上指定数据源的名称
 * @author: Sinda
 * @create: 2019-09-03
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface ChooseDataSource {
    /**
     * 此处接收的是数据源的名称,不填默认第一个
     */
    String value() default "";
}
