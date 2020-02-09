package com.zsinda.fdp.config;

import com.zsinda.fdp.FdpRedissonLock;
import com.zsinda.fdp.FdpRedissonManager;
import com.zsinda.fdp.aspect.LockAspect;
import com.zsinda.fdp.operation.RedissonBinary;
import com.zsinda.fdp.operation.RedissonCollection;
import com.zsinda.fdp.operation.RedissonObject;
import com.zsinda.fdp.properties.FdpRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @program: FDPlatform
 * @description: redission 自动配置
 * @author: Sinda
 * @create: 2020-02-04 10:07
 */
@Slf4j
@ConditionalOnExpression("${spring.redis.lock.enable:false}")
public class RedissonAutoConfiguration {

    @Bean
    public LockAspect distributedLockHandler(FdpRedissonManager redissonManager) {
        return new LockAspect(redissonLock(redissonManager));
    }

    public FdpRedissonLock redissonLock(FdpRedissonManager redissonManager) {
        FdpRedissonLock redissonLock = new FdpRedissonLock(redissonManager.getRedisson());
        log.info("[RedissonLock]组装完毕");
        return redissonLock;
    }

    @Bean
    @ConditionalOnMissingBean
    public FdpRedissonManager redissonManager(FdpRedisProperties redisProperties) {
        FdpRedissonManager redissonManager =
                new FdpRedissonManager(redisProperties);
        log.info("[RedissonManager]组装完毕,当前连接方式:{}",redisProperties.getType());
        return redissonManager;
    }

    @Bean
    @ConditionalOnMissingBean(RedissonBinary.class)
    public RedissonBinary RedissonBinary(FdpRedissonManager redissonManager) {
        return new RedissonBinary(redissonManager.getRedisson());
    }

    @Bean
    @ConditionalOnMissingBean(RedissonObject.class)
    public RedissonObject RedissonObject(FdpRedissonManager redissonManager,FdpRedisProperties redisProperties) {
        return new RedissonObject(redissonManager.getRedisson(),redisProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonCollection.class)
    public RedissonCollection RedissonCollection(FdpRedissonManager redissonManager,FdpRedisProperties redisProperties) {
        return new RedissonCollection(redissonManager.getRedisson(),redisProperties);
    }
}
