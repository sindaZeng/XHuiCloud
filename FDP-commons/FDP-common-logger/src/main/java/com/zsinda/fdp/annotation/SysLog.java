package com.zsinda.fdp.annotation;

import java.lang.annotation.*;


/**
 * @author sinda
 * @date 2020年01月31日20:35:00
 * @description 操作 日志注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    /**
     * 描述
     *
     * @return {String}
     */
    String value();

}
