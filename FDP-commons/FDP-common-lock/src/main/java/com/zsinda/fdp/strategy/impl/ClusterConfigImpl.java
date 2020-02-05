package com.zsinda.fdp.strategy.impl;

import com.zsinda.fdp.enums.RedissonEnum;
import com.zsinda.fdp.properties.RedissonProperties;
import com.zsinda.fdp.strategy.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

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
    public Config createConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            //设置cluster节点的服务IP和端口
            for (int i = 0; i < addrTokens.length; i++) {
                config.useClusterServers()
                        .addNodeAddress(RedissonEnum.REDIS_CONNECTION_PREFIX.getType() + addrTokens[i]);
                if (StringUtils.isNotBlank(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            log.info("初始化[集群部署]方式Config,redisAddress: [{}]",address);
        } catch (Exception e) {
            log.error("集群部署 Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}
