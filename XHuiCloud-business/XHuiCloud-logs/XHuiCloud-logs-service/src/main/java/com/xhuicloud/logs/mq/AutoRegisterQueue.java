package com.xhuicloud.logs.mq;

import com.xhuicloud.common.log.constant.LogConstant;
import com.xhuicloud.common.mq.properties.XHuiRabbitMqProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/1
 */
@Component
public class AutoRegisterQueue {

    @Bean
    public XHuiRabbitMqProperties xHuiRabbitMqProperties() {
        XHuiRabbitMqProperties xHuiRabbitMqProperties = new XHuiRabbitMqProperties();
        xHuiRabbitMqProperties.setQueues(Arrays.asList(LogConstant.AUDIT_RECORD_QUEUE_NAME, LogConstant.LOGIN_RECORD_QUEUE_NAME));
        return xHuiRabbitMqProperties;
    }
}
