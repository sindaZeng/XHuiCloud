package com.xhuicloud.common.datasource;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.xhuicloud.common.datasource.config.DataSourceProperties;
import com.xhuicloud.common.datasource.config.TenantDsProcessor;
import com.xhuicloud.common.datasource.config.WebMvcConfig;
import com.xhuicloud.common.datasource.config.XhuiDynamicDataSourceProvider;
import com.xhuicloud.common.datasource.tenant.XHuiTenantHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class XHuiDdsAutoConfiguration {

    private final DataSourceProperties properties;

    @Bean
    public XHuiTenantHandler tenantHandler() {
        return new XHuiTenantHandler();
    }

    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new XhuiDynamicDataSourceProvider(properties);
    }

    @Bean
    public DsProcessor dsProcessor() {
        return new TenantDsProcessor();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfig();
    }


}
