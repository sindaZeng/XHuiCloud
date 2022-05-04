package com.xhuicloud.common.mq.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public interface XHuiRabbitMqCallback {

    default void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }

    default void returnedMessage(ReturnedMessage returnedMessage) {

    }
}
