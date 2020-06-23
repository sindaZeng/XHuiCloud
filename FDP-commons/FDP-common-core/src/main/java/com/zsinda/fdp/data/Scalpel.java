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

    /**
     * 域
     *
     * @return
     */
    String domain() default "http://cdn.zsinda.cn/";

    /**
     * 打码保留前几位 默认保留前2
     * 注意: 如果长度小于等于 before() + after() 将不打码
     */
    int before() default 2;

    /**
     * 打码保留后几位 默认保留后2
     */
    int after() default 2;

    /**
     * 打码符号
     */
    String mosaicStr() default "*";
}
