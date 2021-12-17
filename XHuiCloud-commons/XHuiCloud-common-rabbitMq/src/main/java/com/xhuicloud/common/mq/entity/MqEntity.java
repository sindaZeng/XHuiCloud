package com.xhuicloud.common.mq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqEntity implements Serializable {

    /**
     * 消息ID，用于需要做幂等校验的场景
     */
    private String msgId;

    /**
     * 消费错误时的错误信息
     */
    private String errorMsg;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 消费次数
     */
    private Integer times = 0;

}

