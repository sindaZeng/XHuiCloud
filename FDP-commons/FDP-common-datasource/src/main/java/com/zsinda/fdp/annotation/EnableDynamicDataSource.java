package com.zsinda.fdp.annotation;


import com.zsinda.fdp.mybatis.DynamicDataSourceConfig;
import com.zsinda.fdp.properties.DataSourceCoreProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author X
 * @date 2019/12/15
 * 添加此注解表示 开启动态数据源 也可以添加一个数据源
 * 添加多数据源后 mapper需要指定将要使用到的数据源 用 @ChooseDataSource(数据源名称) 表示。如果没有这个数据源，将使用默认数据源
 * 注意：开启动态数据源后。配置文件格式将有所变动
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DynamicDataSourceConfig.class, DataSourceCoreProperties.class})
public @interface EnableDynamicDataSource {
}
