package com.xhuicloud.common.mq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("xhuicloud.mq")
public class XHuiRabbitMqProperties {

    /**
     * direct exchange
     */
    private String directExchange = "xhuicloud.direct.exchange";

    /**
     * delayed exchange
     */
    private String delayedExchange = "xhuicloud.delayed.exchange";

    /**
     * 队列
     */
    private List<String> queues;

}
