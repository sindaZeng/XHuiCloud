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
