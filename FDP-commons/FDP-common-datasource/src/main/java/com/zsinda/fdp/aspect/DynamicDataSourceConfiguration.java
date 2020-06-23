package com.zsinda.fdp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @program: FDPlatform
 * @description: DynamicDataSourceConfiguration
 * @author: Sinda
 * @create: 2020-06-22 17:51
 */

@Slf4j
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
