package com.zsinda.fdp.configuration;

import com.zsinda.fdp.base.IDGenerate;
import com.zsinda.fdp.properties.DefaultSnowflakeProperties;
import com.zsinda.fdp.snowflake.DefaultSnowflakeIDGenerateBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @program: FDPlatform
 * @description: ZeroAutoConfiguration
 * @author: Sinda
 * @create: 2020-03-05 17:08
 */
@ConditionalOnMissingBean(IDGenerate.class)
public class ZeroAutoConfiguration {

    /**
     * 自动配置
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnBean(DefaultSnowflakeProperties.class)
    public IDGenerate defaultSnowflakeIDGenerate(DefaultSnowflakeProperties properties) {
        return DefaultSnowflakeIDGenerateBuilder
                .create()
                .zkAddress(properties.getZkAddress())
                .port(properties.getPort())
                .build();
    }

}
