package com.xhuicloud.common.datasource.aspect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @program: XHuiCloud
 * @description: DynamicDataSourceConfiguration
 * @author: Sinda
 * @create: 2020-06-22 17:51
 */

@Configuration
public class DynamicDataSourceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        DynamicDataSourceInterceptor interceptor = new DynamicDataSourceInterceptor();
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(interceptor);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }
}
