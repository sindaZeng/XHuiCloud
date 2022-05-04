package com.xhuicloud.common.mq.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public class XHuiDefaultRabbitMqCallback implements XHuiRabbitMqCallback{
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {

    }
}
