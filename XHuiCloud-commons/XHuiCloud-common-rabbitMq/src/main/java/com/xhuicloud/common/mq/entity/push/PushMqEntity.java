package com.xhuicloud.common.mq.entity.push;

import com.xhuicloud.common.mq.entity.MqEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushMqEntity extends MqEntity {

    private Class cls;

    /**
     * 推送的载体
     */
    private String json;
}
