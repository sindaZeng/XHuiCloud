package com.xhuicloud.common.datasource.source;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.xhuicloud.common.datasource.tenant.XHuiTenantHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: XHuiCloud
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
    public MybatisPlusInterceptor mybatisPlusInterceptor(XHuiTenantHandler tenantHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantHandler));
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}
