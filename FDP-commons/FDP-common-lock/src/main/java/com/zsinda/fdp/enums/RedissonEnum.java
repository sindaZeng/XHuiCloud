package com.zsinda.fdp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: FDPlatform
 * @description:  Redisson 枚举类
 * @author: Sinda
 * @create: 2020-02-04 10:32
 */
@Getter
@AllArgsConstructor
public enum RedissonEnum {

    REDIS_CONNECTION_PREFIX("redis://", "Redis地址配置前缀"),
    STANDALONE("standalone", "单节点部署方式"),
    MASTERSLAVE("masterslave", "主从部署方式"),
    SENTINEL("sentinel", "哨兵部署方式"),
    CLUSTER("cluster", "集群方式");

    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String description;
}
