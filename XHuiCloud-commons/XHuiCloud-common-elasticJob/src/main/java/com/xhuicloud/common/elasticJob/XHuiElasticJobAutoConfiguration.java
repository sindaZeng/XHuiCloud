package com.xhuicloud.common.elasticJob;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.xhuicloud.common.elasticJob.basejob.AllJobInitialize;
import com.xhuicloud.common.elasticJob.properties.ElasticJobProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @program: XHuiCloud
 * @description: ElasticJobAutoConfiguration
 * @author: Sinda
 * @create: 2020-03-05 23:21
 */
@EnableConfigurationProperties(ElasticJobProperties.class)
public class XHuiElasticJobAutoConfiguration {

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public CoordinatorRegistryCenter elasticJobRegistryCenter(ElasticJobProperties elasticJobProperties) {
        ElasticJobProperties.ZkConfiguration zookeeper = elasticJobProperties.getZookeeper();
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(zookeeper.getServerLists(), zookeeper.getNamespace());
        BeanUtils.copyProperties(zookeeper,zookeeperConfiguration);
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    @ConditionalOnBean(CoordinatorRegistryCenter.class)
    public AllJobInitialize allJobInitialize() {
        return new AllJobInitialize();
    }

}
