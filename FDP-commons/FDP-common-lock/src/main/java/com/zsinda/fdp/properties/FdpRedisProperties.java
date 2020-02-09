package com.zsinda.fdp.properties;

import com.zsinda.fdp.enums.RedissonEnum;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: FDPlatform
 * @description: RedissonProperties
 * @author: Sinda
 * @create: 2020-02-04 10:39
 */
@Data
@Primary
@Component("redisProperties")
@ConfigurationProperties(prefix = "spring.redis")
public class FdpRedisProperties extends RedisProperties {

    /**
     * lock type
     */
    private RedissonEnum type;

    /**
     * 数据缓存时间 默认30分钟
     */
    private Long dataValidTime = 1000 * 60 * 30L;

    /**
     * master - slave
     */
    private List<Slave> slaves;

    /**
     * slave properties.
     */
    @Data
    public static class Slave {

        /**
         * slave host
         */
        private String host = "localhost";

        /**
         * slave port
         */
        private int port = 6379;

    }

}
