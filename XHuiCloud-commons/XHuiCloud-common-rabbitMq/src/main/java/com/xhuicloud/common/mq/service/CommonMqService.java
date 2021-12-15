package com.xhuicloud.common.mq.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.xhuicloud.common.mq.constant.XHuiRabbitMqConstant;
import com.xhuicloud.common.mq.entity.MqEntity;
import com.xhuicloud.common.mq.properties.XHuiRabbitMqProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;

@Slf4j
@AllArgsConstructor
public class CommonMqService {

    private final RabbitTemplate rabbitTemplate;

    private final XHuiRabbitMqProperties xHuiRabbitMqProperties;

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
        rabbitTemplate.convertAndSend(xHuiRabbitMqProperties.getDirectExchange(), queue, entity);
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
        rabbitTemplate.convertAndSend(xHuiRabbitMqProperties.getDirectExchange(), XHuiRabbitMqConstant.ERROR_QUEUE_PREFIX + queue, entity);
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
     * @param queueName
     * @param entity
     * @param delayedSecond 延时秒数
     */
    public void sendDelayed(String queueName, MqEntity entity, Long delayedSecond) {
        log.info("发送延时消息 queueName:{} data: {} delayedSecond: {}", queueName, entity, delayedSecond);
        rabbitTemplate.convertAndSend(xHuiRabbitMqProperties.getDelayedExchange(), queueName, entity, message -> {
            message.getMessageProperties().setHeader("x-delay", delayedSecond * 1000);
            return message;
        });
    }
}
