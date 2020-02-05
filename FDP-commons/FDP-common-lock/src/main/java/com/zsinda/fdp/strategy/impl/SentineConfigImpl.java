package com.zsinda.fdp.strategy.impl;

import com.zsinda.fdp.enums.RedissonEnum;
import com.zsinda.fdp.properties.RedissonProperties;
import com.zsinda.fdp.strategy.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

/**
 * @program: FDPlatform
 * @description: 哨兵集群部署Redis连接配置
 * @author: Sinda
 * @create: 2020-02-04 10:58
 */
@Slf4j
public class SentineConfigImpl implements ConfigService {
    @Override
    public Config createConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            //设置redis配置文件sentinel.conf配置的sentinel别名
            config.useSentinelServers().setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            //设置sentinel节点的服务IP和端口
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(RedissonEnum.REDIS_CONNECTION_PREFIX.getType() + addrTokens[i]);
            }
            log.info("初始化[哨兵部署]方式Config,redisAddress: [{}]",address);
        } catch (Exception e) {
            log.error("哨兵部署 Redisson init error", e);

        }
        return config;
    }
}
