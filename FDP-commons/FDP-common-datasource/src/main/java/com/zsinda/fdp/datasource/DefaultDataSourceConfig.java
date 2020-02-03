package com.zsinda.fdp.datasource;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zsinda.fdp.properties.DataSourceCoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @program: FDPlatform
 * @description: 默认数据源配置
 * @author: Sinda
 * @create: 2020-01-29 15:30
 */
@Slf4j
@ConditionalOnMissingBean(DynamicDataSourceConfig.class)
public class DefaultDataSourceConfig extends BaseDataSourceConfig {

    public DefaultDataSourceConfig(DataSourceCoreProperties dataSourceCoreProperties, PaginationInterceptor paginationInterceptor) {
        super(dataSourceCoreProperties, paginationInterceptor);
    }

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource);
    }


}

