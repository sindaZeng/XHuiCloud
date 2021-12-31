/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

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
