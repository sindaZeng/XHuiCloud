package com.xhuicloud.common.lock;

import com.google.common.base.Preconditions;
import com.xhuicloud.common.lock.enums.RedissonEnum;
import com.xhuicloud.common.lock.properties.XHuiRedisProperties;
import com.xhuicloud.common.lock.strategy.ConfigService;
import com.xhuicloud.common.lock.strategy.impl.ClusterConfigImpl;
import com.xhuicloud.common.lock.strategy.impl.MasterslaveConfigImpl;
import com.xhuicloud.common.lock.strategy.impl.SentineConfigImpl;
import com.xhuicloud.common.lock.strategy.impl.StandaloneConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;

/**
 * @program: XHuiCloud
 * @description: RedissonManager
 * @author: Sinda
 * @create: 2020-02-04 11:07
 */
@Slf4j
public class XHuiRedissonManager {

    private Config config = new Config();

    private Redisson redisson = null;

    public XHuiRedissonManager() {
    }

    public XHuiRedissonManager(XHuiRedisProperties XHuiRedisProperties) {
        try {
            //通过不同部署方式获得不同cofig实体
            config = RedissonConfigFactory.getInstance().createConfig(XHuiRedisProperties);
            redisson = (Redisson) Redisson.create(config);
        } catch (Exception e) {
            log.error("Redisson init error {}", e);
            throw new IllegalArgumentException("please input correct configurations," +
                    "connectionType must in standalone/sentinel/cluster/masterslave");
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }

    /**
     * Redisson连接方式配置工厂
     * 双重检查锁
     */
    static class RedissonConfigFactory {

        private RedissonConfigFactory() {
        }

        private static volatile RedissonConfigFactory factory = null;

        public static RedissonConfigFactory getInstance() {
            if (factory == null) {
                synchronized (Object.class) {
                    if (factory == null) {
                        factory = new RedissonConfigFactory();
                    }
                }
            }
            return factory;
        }

        /**
         * 根据连接类型获取对应连接方式的配置,基于策略模式
         *
         * @param redisProperties redis连接信息
         * @return Config
         */
        Config createConfig(XHuiRedisProperties redisProperties) {
            Preconditions.checkNotNull(redisProperties);
            Preconditions.checkNotNull(redisProperties.getType(), "redisson.lock.io.seata.server.password cannot be NULL");
            Preconditions.checkNotNull(redisProperties.getDatabase(), "redisson.lock.io.seata.server.database cannot be NULL");
            RedissonEnum redissonEnum = redisProperties.getType();
            String type = redissonEnum.getType();
            //声明配置上下文
            ConfigService redissonConfigService;
            if (type.equals(RedissonEnum.STANDALONE.getType())) {
                redissonConfigService = new StandaloneConfigImpl();
            } else if (type.equals(RedissonEnum.SENTINEL.getType())) {
                redissonConfigService = new SentineConfigImpl();
            } else if (type.equals(RedissonEnum.CLUSTER.getType())) {
                redissonConfigService = new ClusterConfigImpl();
            } else if (type.equals(RedissonEnum.MASTERSLAVE.getType())) {
                redissonConfigService = new MasterslaveConfigImpl();
            } else {
                throw new IllegalArgumentException("创建Redisson连接Config失败！当前连接方式:" + type);
            }
            return redissonConfigService.createConfig(redisProperties);
        }
    }

}
