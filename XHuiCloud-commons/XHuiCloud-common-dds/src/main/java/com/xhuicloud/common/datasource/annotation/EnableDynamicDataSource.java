package com.xhuicloud.common.datasource.annotation;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.xhuicloud.common.datasource.XHuiDdsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2019-12-15
 **/
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAutoConfiguration(exclude = DruidDataSourceAutoConfigure.class )
@Import(XHuiDdsAutoConfiguration.class)
public @interface EnableDynamicDataSource {
}
