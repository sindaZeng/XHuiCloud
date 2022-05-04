package com.xhuicloud.common.mq.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    private final RabbitTemplate rabbitTemplate;

    private final XHuiRabbitMqCallback xHuiRabbitMqCallback;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 确认回调。默认不开启
     * 当开启这个模式之后，生产者个交换机发送一个消息，交换机收到这个消息之后，
     * 会给消费者发送一个确认收到的信息。
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.warn("消息 exchange:[{}]", correlationData);
        log.warn("消息 确认结果:[{}]", ack);
        log.warn("消息 失败原因:[{}]", cause);
        if (xHuiRabbitMqCallback != null) {
            xHuiRabbitMqCallback.confirm(correlationData, ack, cause);
        }
    }

    /**
     * 在交换机到达队列失败的时候才会被触发
     * 当这个回调函数被调用的时候
     * 比如队列已满
     *
     * @param returnedMessage
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.warn("消息投递失败处理 exchange:[{}]", returnedMessage.getExchange());
        log.warn("消息投递失败处理 routing:[{}]", returnedMessage.getRoutingKey());
        if (xHuiRabbitMqCallback != null) {
            xHuiRabbitMqCallback.returnedMessage(returnedMessage);
        }
    }
}
