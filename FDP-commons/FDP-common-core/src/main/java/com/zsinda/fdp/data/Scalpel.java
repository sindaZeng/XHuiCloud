package com.zsinda.fdp.data;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: FDPlatform
 * @description: 手术刀: 运行时修改数据
 * @author: Sinda
 * @create: 2020-05-29 17:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = ScalpelSerialize.class)
public @interface Scalpel {

    ScalpelTypeEnum type() default ScalpelTypeEnum.CUSTOMER;

    String domain() default "http://cdn.zsinda.cn/";
}
