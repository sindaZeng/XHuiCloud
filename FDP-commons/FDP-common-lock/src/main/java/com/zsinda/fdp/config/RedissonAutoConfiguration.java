package com.zsinda.fdp.config;

import com.zsinda.fdp.RedissonLock;
import com.zsinda.fdp.RedissonManager;
import com.zsinda.fdp.operation.RedissonBinary;
import com.zsinda.fdp.operation.RedissonCollection;
import com.zsinda.fdp.operation.RedissonObject;
import com.zsinda.fdp.properties.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @program: FDPlatform
 * @description: redission 自动配置
 * @author: Sinda
 * @create: 2020-02-04 10:07
 */
@Slf4j
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public RedissonLock redissonLock(RedissonManager redissonManager) {
        RedissonLock redissonLock = new RedissonLock(redissonManager.getRedisson());
        log.info("[RedissonLock]组装完毕");
        return redissonLock;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonManager redissonManager(RedissonProperties redissonProperties) {
        RedissonManager redissonManager =
                new RedissonManager(redissonProperties);
        log.info("[RedissonManager]组装完毕,当前连接方式:[{}],连接地址:[{}]",
                redissonProperties.getType(),redissonProperties.getAddress());
        return redissonManager;
    }

    @Bean
    @ConditionalOnMissingBean(RedissonBinary.class)
    public RedissonBinary RedissonBinary(RedissonManager redissonManager) {
        return new RedissonBinary(redissonManager.getRedisson());
    }

    @Bean
    @ConditionalOnMissingBean(RedissonObject.class)
    public RedissonObject RedissonObject(RedissonManager redissonManager, RedissonProperties redissonProperties) {
        return new RedissonObject(redissonManager.getRedisson(),redissonProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonCollection.class)
    public RedissonCollection RedissonCollection(RedissonManager redissonManager,RedissonProperties redissonProperties) {
        return new RedissonCollection(redissonManager.getRedisson(),redissonProperties);
    }
}
