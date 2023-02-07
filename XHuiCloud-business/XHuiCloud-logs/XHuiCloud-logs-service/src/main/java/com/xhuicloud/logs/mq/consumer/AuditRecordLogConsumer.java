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

package com.xhuicloud.logs.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xhuicloud.common.core.utils.BeanUtils;
import com.xhuicloud.common.core.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.common.log.constant.LogConstant;
import com.xhuicloud.common.log.model.AuditModel;
import com.xhuicloud.common.mq.entity.push.PushMqEntity;
import com.xhuicloud.logs.entity.AuditRecordModel;
import com.xhuicloud.logs.service.AuditRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class AuditRecordLogConsumer {

    private final AuditRecordService auditRecordService;

    @RabbitHandler
    @RabbitListener(queues = LogConstant.AUDIT_RECORD_QUEUE_NAME)
    public void consumePush(PushMqEntity pushMqEntity, Channel channel, Message message) throws IOException {
        try {
            AuditModel auditModel = JSON.parseObject(pushMqEntity.getJson(), AuditModel.class);
            AuditRecordModel auditRecordModel = BeanUtils.copy(auditModel, AuditRecordModel.class);
            auditRecordService.save(auditRecordModel);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
        XHuiCommonThreadLocalHolder.removeTenant();
    }

}
