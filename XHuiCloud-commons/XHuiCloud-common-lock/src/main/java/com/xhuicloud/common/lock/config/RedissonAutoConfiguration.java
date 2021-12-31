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

package com.xhuicloud.common.lock.config;

import com.xhuicloud.common.lock.XHuiRedissonLock;
import com.xhuicloud.common.lock.XHuiRedissonManager;
import com.xhuicloud.common.lock.aspect.LockAspect;
import com.xhuicloud.common.lock.operation.RedissonBinary;
import com.xhuicloud.common.lock.operation.RedissonCollection;
import com.xhuicloud.common.lock.operation.RedissonObject;
import com.xhuicloud.common.lock.properties.XHuiRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @program: XHuiCloud
 * @description: redission 自动配置
 * @author: Sinda
 * @create: 2020-02-04 10:07
 */
@Slf4j
@ConditionalOnExpression("${spring.redis.lock.enable:false}")
public class RedissonAutoConfiguration {

    @Bean
    public LockAspect distributedLockHandler(XHuiRedissonManager redissonManager) {
        return new LockAspect(redissonLock(redissonManager));
    }

    public XHuiRedissonLock redissonLock(XHuiRedissonManager redissonManager) {
        XHuiRedissonLock redissonLock = new XHuiRedissonLock(redissonManager.getRedisson());
        log.info("[RedissonLock]组装完毕");
        return redissonLock;
    }

    @Bean
    @ConditionalOnMissingBean
    public XHuiRedissonManager redissonManager(XHuiRedisProperties redisProperties) {
        XHuiRedissonManager redissonManager =
                new XHuiRedissonManager(redisProperties);
        log.info("[RedissonManager]组装完毕,当前连接方式:{}",redisProperties.getType());
        return redissonManager;
    }

    @Bean
    @ConditionalOnMissingBean(RedissonBinary.class)
    public RedissonBinary RedissonBinary(XHuiRedissonManager redissonManager) {
        return new RedissonBinary(redissonManager.getRedisson());
    }

    @Bean
    @ConditionalOnMissingBean(RedissonObject.class)
    public RedissonObject RedissonObject(XHuiRedissonManager redissonManager, XHuiRedisProperties redisProperties) {
        return new RedissonObject(redissonManager.getRedisson(),redisProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonCollection.class)
    public RedissonCollection RedissonCollection(XHuiRedissonManager redissonManager, XHuiRedisProperties redisProperties) {
        return new RedissonCollection(redissonManager.getRedisson(),redisProperties);
    }
}
