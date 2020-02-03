package com.zsinda.fdp.properties;


import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author X
 * @date 2019/09/03
 */
@Data
@Component
@ConditionalOnExpression("!'${spring.datasources}'.isEmpty()")
@ConfigurationProperties(prefix = "spring")
public class DataSourceCoreProperties {

    private Map<String, DruidDataSource> datasources;

    private String[] mapperLocations = new String[]{"classpath*:/mapper/**/*.xml"};

}
