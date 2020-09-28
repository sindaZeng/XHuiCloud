package com.xhuicloud.common.lock.strategy.impl;

import com.xhuicloud.common.lock.enums.RedissonEnum;
import com.xhuicloud.common.lock.properties.XHuiRedisProperties;
import com.xhuicloud.common.lock.strategy.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

import java.util.List;

/**
 * @program: XHuiCloud
 * @description: 哨兵集群部署Redis连接配置
 * @author: Sinda
 * @create: 2020-02-04 10:58
 */
@Slf4j
public class SentineConfigImpl implements ConfigService {
    @Override
    public Config createConfig(XHuiRedisProperties redisProperties) {
        Config config = new Config();
        try {
            String password = redisProperties.getPassword();
            int database = redisProperties.getDatabase();
            List<String> nodes = redisProperties.getSentinel().getNodes();
            //设置redis配置文件sentinel.conf配置的sentinel别名
            config.useSentinelServers().setMasterName(redisProperties.getSentinel().getMaster());
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            //设置sentinel节点的服务IP和端口
            nodes.forEach(node->{
                config.useSentinelServers().addSentinelAddress(RedissonEnum.REDIS_CONNECTION_PREFIX.getType() + node);
            });

            log.info("初始化[哨兵部署]方式Config,redisAddress:{}",nodes);
        } catch (Exception e) {
            log.error("哨兵部署 Redisson init error", e);

        }
        return config;
    }
}
