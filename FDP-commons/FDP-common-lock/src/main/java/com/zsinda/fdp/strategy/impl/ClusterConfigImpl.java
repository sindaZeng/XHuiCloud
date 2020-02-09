package com.zsinda.fdp.strategy.impl;

import com.zsinda.fdp.enums.RedissonEnum;
import com.zsinda.fdp.properties.FdpRedisProperties;
import com.zsinda.fdp.strategy.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

import java.util.List;

/**
 * @program: FDPlatform
 * @description: 集群方式Redisson部署
 * 地址格式：
 * cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
 * 格式为: 127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381,127.0.0.1:6382,127.0.0.1:6383,127.0.0.1:6384
 * @author: Sinda
 * @create: 2020-02-04 10:52
 */
@Slf4j
public class ClusterConfigImpl implements ConfigService {
    @Override
    public Config createConfig(FdpRedisProperties redisProperties) {
        Config config = new Config();
        try {
            List<String> nodes = redisProperties.getCluster().getNodes();
            String password = redisProperties.getPassword();
            //设置cluster节点的服务IP和端口
            nodes.forEach(node->{
                config.useClusterServers()
                        .addNodeAddress(RedissonEnum.REDIS_CONNECTION_PREFIX.getType() + node);
                if (StringUtils.isNotBlank(password)) {
                    config.useClusterServers().setPassword(password);
                }
            });
            log.info("初始化[集群部署]方式Config,redisAddress:{}",nodes.toString());
        } catch (Exception e) {
            log.error("集群部署 Redisson init error{}", e);
            e.printStackTrace();
        }
        return config;
    }
}
