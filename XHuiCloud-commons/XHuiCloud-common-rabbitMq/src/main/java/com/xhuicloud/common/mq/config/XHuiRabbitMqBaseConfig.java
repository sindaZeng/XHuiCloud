package com.xhuicloud.common.mq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.common.mq.aspect.MqListenerAop;
import com.xhuicloud.common.mq.properties.XHuiRabbitMqProperties;
import com.xhuicloud.common.mq.registrar.XHuiRabbitAutoRegister;
import com.xhuicloud.common.mq.service.CommonMqService;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class XHuiRabbitMqBaseConfig {

    public static final String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * 消息序列化方式配置
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public XHuiRabbitAutoRegister xHuiRabbitAutoRegister(XHuiRabbitMqProperties xHuiRabbitMqProperties,
                                                         ConfigurableApplicationContext applicationContext,
                                                         CustomExchange delayedExchange,
                                                         DirectExchange directExchange) {
        return new XHuiRabbitAutoRegister(xHuiRabbitMqProperties, applicationContext, delayedExchange, directExchange);
    }

    /**
     * 消息序列化方式配置
     *
     * @param objectMapper
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        objectMapper.setDateFormat(new SimpleDateFormat(UTC_MS_WITH_ZONE_OFFSET_PATTERN));
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * 通用的 direct exchange
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DirectExchange directExchange(XHuiRabbitMqProperties xHuiRabbitMqProperties) {
        return new DirectExchange(xHuiRabbitMqProperties.getDirectExchange(), true, false);
    }

    /**
     * 通用的延时 exchange
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public CustomExchange delayedExchange(XHuiRabbitMqProperties xHuiRabbitMqProperties) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(xHuiRabbitMqProperties.getDelayedExchange(), "x-delayed-message", true, false, args);
    }

    @Bean
    public CommonMqService commonMqService(RabbitTemplate rabbitTemplate, XHuiRabbitMqProperties xHuiRabbitMqProperties) {
        return new CommonMqService(rabbitTemplate, xHuiRabbitMqProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public MqListenerAop mqListenerAop(CommonMqService commonMqService) {
        return new MqListenerAop(commonMqService);
    }

}
