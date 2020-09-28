package com.xhuicloud.common.zero.configuration;

import com.xhuicloud.common.zero.base.IDGenerate;
import com.xhuicloud.common.zero.properties.DefaultSnowflakeProperties;
import com.xhuicloud.common.zero.snowflake.DefaultSnowflakeIDGenerateBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @program: XHuiCloud
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
