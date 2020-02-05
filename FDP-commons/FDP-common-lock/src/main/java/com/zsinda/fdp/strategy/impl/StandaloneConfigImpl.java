package com.zsinda.fdp.strategy.impl;

import com.zsinda.fdp.enums.RedissonEnum;
import com.zsinda.fdp.properties.RedissonProperties;
import com.zsinda.fdp.strategy.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

/**
 * @program: FDPlatform
 * @description: 单机部署Redisson配置
 * @author: Sinda
 * @create: 2020-02-04 11:04
 */
@Slf4j
public class StandaloneConfigImpl implements ConfigService {
    @Override
    public Config createConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = RedissonEnum.REDIS_CONNECTION_PREFIX.getType() + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            //密码可以为空
            if (StringUtils.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            log.info("初始化[单机部署]方式Config,redisAddress: [{}]",address);
        } catch (Exception e) {
            log.error("单机部署 Redisson init error", e);
        }
        return config;
    }
}
