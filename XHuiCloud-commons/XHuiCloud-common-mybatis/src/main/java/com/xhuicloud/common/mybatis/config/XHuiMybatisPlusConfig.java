package com.xhuicloud.common.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.xhuicloud.common.mybatis.resolver.SqlFilterResolver;
import com.xhuicloud.common.mybatis.tenant.XHuiTenantHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: 分页插件
 * @author: Sinda
 * @create: 2019-12-15
 **/
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class XHuiMybatisPlusConfig implements WebMvcConfigurer {

    /**
     * 增加请求参数解析器，对请求中的参数注入SQL 检查
     * @param resolverList
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList) {
        resolverList.add(new SqlFilterResolver());
    }

    /**
     * 数据处理
     */
    /**
     * mybatis plus 拦截器配置
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(XHuiTenantHandler xHuiTenantHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 多租户支持
        TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
        tenantLineInnerInterceptor.setTenantLineHandler(xHuiTenantHandler);
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * SQL 日志格式化
     */
    @Bean
    public DruidSqlLogFilterAdapter druidSqlLogFilterAdapter() {
        return new DruidSqlLogFilterAdapter();
    }

}
