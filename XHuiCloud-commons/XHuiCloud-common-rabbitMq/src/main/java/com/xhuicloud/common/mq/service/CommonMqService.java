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

package com.xhuicloud.common.mq.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.xhuicloud.common.mq.constant.XHuiRabbitMqConstant;
import com.xhuicloud.common.mq.entity.MqEntity;
import com.xhuicloud.common.mq.properties.XHuiRabbitMqProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class CommonMqService {

    private final RabbitTemplate rabbitTemplate;

    private final XHuiRabbitMqProperties xHuiRabbitMqProperties;

    // 默认不持久化
    private MessageDeliveryMode messageDeliveryMode = MessageDeliveryMode.NON_PERSISTENT;

    public CommonMqService persistent(boolean persistent) {
        if (persistent) {
            this.messageDeliveryMode = MessageDeliveryMode.PERSISTENT;
        }
        return this;
    }

    /**
     * 向 queue 队列发送 data 的json数据
     *
     * @param queue
     * @param entity
     * @param sendTime
     */
    public void send(String queue, MqEntity entity, Date sendTime) {
        if (null != sendTime) {
            sendTiming(queue, entity, sendTime);
        } else {
            sendDirect(queue, entity);
        }
    }

    /**
     * 向 queue 队列发送 data 的json数据
     *
     * @param entity
     */
    public void sendDirect(String queue, MqEntity entity) {
        rabbitTemplate.convertAndSend(xHuiRabbitMqProperties.getDirectExchange(), queue, getMessage(entity), (CorrelationData) null);
    }

    /**
     * 向 queue 队列发送 data 的json数据，增加Exception信息
     *
     * @param queue
     * @param entity
     * @param throwable
     */
    public void sendError(String queue, MqEntity entity, Throwable throwable) {
        entity.setErrorMsg(String.format("{%s}:{%s}", throwable.getClass().getSimpleName(), throwable.getMessage()));
        rabbitTemplate.convertAndSend(xHuiRabbitMqProperties.getDirectExchange(), XHuiRabbitMqConstant.ERROR_QUEUE_PREFIX + queue, getMessage(entity));
    }

    /**
     * 定时发送
     *
     * @param queueName
     * @param entity
     * @param sendTime
     */
    public void sendTiming(String queueName, MqEntity entity, Date sendTime) {
        if (sendTime.getTime() < System.currentTimeMillis()) {
            sendDirect(queueName, entity);
        } else {
            long delayedSecond = DateUtil.between(sendTime, new Date(), DateUnit.SECOND);
            sendDelayed(queueName, entity, delayedSecond);
        }
    }

    /**
     * 发送延时消息
     *
     * @param queueName
     * @param entity
     * @param delayedSecond 延时秒数
     */
    public void sendDelayed(String queueName, MqEntity entity, Integer delayedSecond) {
        sendDelayed(queueName, entity, Long.valueOf(delayedSecond));
    }

    /**
     * 发送延时消息
     *
     * @param queue
     * @param entity
     * @param delayedSecond 延时秒数
     */
    public void sendDelayed(String queue, MqEntity entity, Long delayedSecond) {
        Message message = getMessage(entity);
        message.getMessageProperties().setHeader("x-delay", delayedSecond * 1000);
        log.info("发送延时消息 queueName:{} data: {} delayedSecond: {}", queue, entity, delayedSecond);
        rabbitTemplate.convertAndSend(xHuiRabbitMqProperties.getDelayedExchange(), queue, message, (CorrelationData) null);
    }

    /**
     * 消息载体
     * @param entity
     * @return
     */
    private Message getMessage(MqEntity entity) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(messageDeliveryMode);
        return rabbitTemplate.getMessageConverter().toMessage(entity, messageProperties);
    }
}
