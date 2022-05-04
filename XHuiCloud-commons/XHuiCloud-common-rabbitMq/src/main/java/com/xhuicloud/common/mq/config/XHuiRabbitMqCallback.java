package com.xhuicloud.common.mq.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public interface XHuiRabbitMqCallback {

    void confirm(CorrelationData correlationData, boolean ack, String cause);

    void returnedMessage(ReturnedMessage returnedMessage);

}
