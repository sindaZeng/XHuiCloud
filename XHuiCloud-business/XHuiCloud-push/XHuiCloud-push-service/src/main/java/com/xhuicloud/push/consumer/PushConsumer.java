package com.xhuicloud.push.consumer;

import com.alibaba.fastjson.JSON;
import com.xhuicloud.common.data.tenant.XHuiCommonThreadLocalHolder;
import com.xhuicloud.common.mq.entity.push.PushMqEntity;
import com.xhuicloud.push.common.PushMultiDiff;
import com.xhuicloud.push.common.PushMultiple;
import com.xhuicloud.push.common.PushSingle;
import com.xhuicloud.push.service.PushCommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PushConsumer {

    public static final String QUEUE_NAME = "push.queue";

    private final PushCommonService pushCommonService;

    @RabbitHandler
    @RabbitListener(queues = QUEUE_NAME)
    public void consumePush(PushMqEntity pushMqEntity) {
        XHuiCommonThreadLocalHolder.setTenant(pushMqEntity.getTenantId());
        if (pushMqEntity.getCls() == PushSingle.class) {
            pushCommonService.single(JSON.parseObject(pushMqEntity.getJson(), PushSingle.class));
        } else if (pushMqEntity.getCls()== PushMultiple.class) {
            pushCommonService.multiple(JSON.parseObject(pushMqEntity.getJson(), PushMultiple.class));
        } else if (pushMqEntity.getCls() == PushMultiDiff.class) {
            pushCommonService.multiDiff(JSON.parseObject(pushMqEntity.getJson(), PushMultiDiff.class));
        }
        XHuiCommonThreadLocalHolder.removeTenant();
    }

}
