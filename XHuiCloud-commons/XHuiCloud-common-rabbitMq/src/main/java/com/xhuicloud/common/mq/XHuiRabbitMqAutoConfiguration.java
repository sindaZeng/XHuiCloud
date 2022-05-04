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

package com.xhuicloud.common.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.common.mq.aspect.MqListenerAop;
import com.xhuicloud.common.mq.config.RabbitTemplateConfig;
import com.xhuicloud.common.mq.config.XHuiDefaultRabbitMqCallback;
import com.xhuicloud.common.mq.config.XHuiRabbitMqCallback;
import com.xhuicloud.common.mq.properties.XHuiRabbitMqProperties;
import com.xhuicloud.common.mq.config.XHuiRabbitAutoRegister;
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
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

public class XHuiRabbitMqAutoConfiguration {

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
    @ConditionalOnMissingBean
    public XHuiDefaultRabbitMqCallback defaultRabbitMqCallback() {
        return new XHuiDefaultRabbitMqCallback();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public CommonMqService commonMqService(RabbitTemplate rabbitTemplate, XHuiRabbitMqProperties xHuiRabbitMqProperties) {
        return new CommonMqService(rabbitTemplate, xHuiRabbitMqProperties);
    }

    @Bean
    public RabbitTemplateConfig rabbitTemplateConfig(RabbitTemplate rabbitTemplate, XHuiRabbitMqCallback xHuiRabbitMqCallback) {
        return new RabbitTemplateConfig(rabbitTemplate, xHuiRabbitMqCallback);
    }

    @Bean
    @ConditionalOnMissingBean
    public MqListenerAop mqListenerAop(CommonMqService commonMqService) {
        return new MqListenerAop(commonMqService);
    }

}
