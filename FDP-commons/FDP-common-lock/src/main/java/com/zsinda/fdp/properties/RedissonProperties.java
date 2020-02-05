package com.zsinda.fdp.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: FDPlatform
 * @description: RedissonProperties
 * @author: Sinda
 * @create: 2020-02-04 10:39
 */
@ConfigurationProperties(prefix = "redisson.lock.server")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedissonProperties {

    /**
     * redis主机地址，ip：port，有多个用半角逗号分隔
     */
    private String address;

    /**
     * 连接类型，支持standalone-单机节点，sentinel-哨兵，cluster-集群，masterslave-主从
     */
    private String type;

    /**
     * redis 连接密码
     */
    private String password;

    /**
     * 选取那个数据库
     */
    private int database;

    /**
     * 数据缓存时间 默认30分钟
     */
    private Long dataValidTime=1000*60* 30L;

}
