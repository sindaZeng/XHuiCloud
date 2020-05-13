package com.zsinda.fdp.datasource;


import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.zsinda.fdp.tenant.FdpTenantHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: FDPlatform
 * @description: 分页插件
 * @author: Sinda
 * @create: 2019-12-15
 **/
@Configuration
public class MybatisPlusConfig {


    /**
     * 插件扩展: 分页插件 租户处理器 sql攻击拦截器
     *
     * @param tenantHandler
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(FdpTenantHandler tenantHandler) {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = new ArrayList<>();

        sqlParserList.add(new FdpJsqlParser());
        // 多租户 处理器
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(tenantHandler);
        sqlParserList.add(tenantSqlParser);

        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

}
