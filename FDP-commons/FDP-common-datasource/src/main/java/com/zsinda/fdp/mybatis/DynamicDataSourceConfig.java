package com.zsinda.fdp.mybatis;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zsinda.fdp.dds.DynamicDataSource;
import com.zsinda.fdp.properties.DataSourceCoreProperties;
import com.zsinda.fdp.transaction.DynamicTransactionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * @author X
 * @date 2019/12/15
 */
@Slf4j
public class DynamicDataSourceConfig {

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();


    @Autowired
    private DataSourceCoreProperties dataSourceCoreProperties;

    @Autowired
    private MetaObjectHandler metaObjectHandler;


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        return paginationInterceptor;
    }

    @Bean(name = "datasources")
    public Map<String, DataSource> datasources() {
        Map<String, DataSource> datasources = new LinkedHashMap();
        Map<String, DruidDataSource> dataSourcesPropertiesMap = dataSourceCoreProperties.getDatasources();
        for (Map.Entry<String, DruidDataSource> entry : dataSourcesPropertiesMap.entrySet()) {
            datasources.put(entry.getKey(), entry.getValue());
        }
        return datasources;
    }

    /**
     * 动态数据源配置
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("datasources") Map<String, DataSource> datasources) {
        log.info("开始 -> 初始化动态数据源");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap(datasources.size());
        for (Map.Entry<String, DataSource> entry : datasources.entrySet()) {
            targetDataSources.put(entry.getKey(), entry.getValue());
            log.info("初始化 -> {} 数据源",entry.getKey());
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        //默认数据源配置 第一个
        dynamicDataSource.setDefaultTargetDataSource(datasources.get(datasources.keySet().iterator().next()));
        log.info("完毕 -> 初始化动态数据源,共计 {} 条", targetDataSources.size());
        return dynamicDataSource;
    }


    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        //扫描xml路径
        sqlSessionFactory.setMapperLocations(resolveMapperLocations());
        //全局自动填充
        sqlSessionFactory.setGlobalConfig(new GlobalConfig().setMetaObjectHandler(metaObjectHandler));
        //自定义 多数据源事务处理
        sqlSessionFactory.setTransactionFactory(new DynamicTransactionFactory(dataSourceCoreProperties));
        //插件
        sqlSessionFactory.setPlugins(new Interceptor[]{
                paginationInterceptor()
        });
        return sqlSessionFactory;
    }


    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(dataSourceCoreProperties.getMapperLocations()).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location)))
                .toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }
}
