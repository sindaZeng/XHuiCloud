package com.xhuicloud.common.lock.strategy.impl;

import com.xhuicloud.common.lock.enums.RedissonEnum;
import com.xhuicloud.common.lock.properties.XHuiRedisProperties;
import com.xhuicloud.common.lock.strategy.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: 主从部署Redisson配置
 * 连接方式:  主节点,子节点,子节点
 * 格式为:  127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381
 * @author: Sinda
 * @create: 2020-02-04 10:54
 */
@Slf4j
public class MasterslaveConfigImpl implements ConfigService {
    @Override
    public Config createConfig(XHuiRedisProperties redisProperties) {
        Config config = new Config();
        try {
            String password = redisProperties.getPassword();
            int database = redisProperties.getDatabase();
            //主节点ip
            String masterNodeAddr = RedissonEnum.REDIS_CONNECTION_PREFIX.getType() +
                    redisProperties.getHost()+":"+redisProperties.getPort();
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (StringUtils.isNotBlank(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            //从节点ip
            List<XHuiRedisProperties.Slave> slaves = redisProperties.getSlaves();
            List<String> slaveList = new ArrayList<>();
            for (XHuiRedisProperties.Slave slave: slaves) {
                slaveList.add(RedissonEnum.REDIS_CONNECTION_PREFIX.getType() +
                        slave.getHost()+":"+slave.getPort());
            }
            config.useMasterSlaveServers().addSlaveAddress(slaveList.toArray(new String[slaveList.size()]));
            log.info("初始化[主从部署]方式Config,master:{},salve:{}",masterNodeAddr,slaveList.toString());
        } catch (Exception e) {
            log.error("主从部署 Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}
