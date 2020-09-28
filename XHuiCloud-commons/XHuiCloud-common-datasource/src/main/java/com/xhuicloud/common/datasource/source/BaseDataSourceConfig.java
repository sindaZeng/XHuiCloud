package com.xhuicloud.common.datasource.source;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.xhuicloud.common.datasource.properties.DataSourceCoreProperties;
import com.xhuicloud.common.datasource.transaction.DynamicTransactionFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.JdbcType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @program: XHuiCloud
 * @description: 基础数据源配置
 * @author: Sinda
 * @create: 2020-01-29 14:46
 */
@Slf4j
@AllArgsConstructor
public class BaseDataSourceConfig {

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    private final DataSourceCoreProperties dataSourceCoreProperties;

    private final MybatisPlusInterceptor mybatisPlusInterceptor;

    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        // 开启分布式事务时 需要传入 new DataSourceProxy(dataSource)
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        //扫描xml路径
        sqlSessionFactory.setMapperLocations(resolveMapperLocations());
        //全局自动填充
//        sqlSessionFactory.setGlobalConfig(new GlobalConfig().setMetaObjectHandler(metaObjectHandler));
        //自定义 多数据源事务处理
        sqlSessionFactory.setTransactionFactory(new DynamicTransactionFactory(dataSourceCoreProperties));
        //插件
        sqlSessionFactory.setPlugins(new Interceptor[]{mybatisPlusInterceptor});
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
