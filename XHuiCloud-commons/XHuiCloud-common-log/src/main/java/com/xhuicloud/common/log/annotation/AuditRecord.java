package com.xhuicloud.common.log.annotation;

import java.lang.annotation.*;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/1/11
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditRecord {

    /**
     * eg: "'修改了：从' + #new1 + ',修改到 '+ #new2"
     * eg:  "'修改了：从' + deliveryUser(#new1) + ',修改到 '+ #new2" 注意:调用的是当前切点类中的方法
     *
     * @return
     */
    String value() default "";

    /**
     * 记录日志的条件
     *
     * @return
     */
    String condition() default "";

    /**
     * 打码字段: 只对请求参数 返回结果有效
     * 默认使用: * 打码
     *
     * @return
     */
    String[] anonymousFields() default {};
}
