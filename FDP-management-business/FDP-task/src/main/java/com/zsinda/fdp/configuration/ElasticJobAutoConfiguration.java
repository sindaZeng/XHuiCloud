package com.zsinda.fdp.configuration;

import com.zsinda.fdp.properties.ElasticJobProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: FDPlatform
 * @description: ElasticJobAutoConfiguration
 * @author: Sinda
 * @create: 2020-03-05 23:21
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(ElasticJobProperties.class)
public class ElasticJobAutoConfiguration {
}
