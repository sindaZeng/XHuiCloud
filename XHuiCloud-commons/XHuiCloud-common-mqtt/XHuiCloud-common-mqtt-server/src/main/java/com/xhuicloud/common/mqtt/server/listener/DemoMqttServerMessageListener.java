package com.xhuicloud.common.mqtt.server.listener;

import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.codec.ByteBufferUtil;
import net.dreamlu.iot.mqtt.core.server.event.IMqttMessageListener;
import net.dreamlu.iot.mqtt.core.server.model.Message;
import net.dreamlu.iot.mqtt.spring.server.MqttServerTemplate;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.tio.core.ChannelContext;

/**
 * @Desc 演示
 * @Author Sinda
 * @Date 2023/2/14
 */
@Slf4j
//@Service
public class DemoMqttServerMessageListener implements IMqttMessageListener, SmartInitializingSingleton {
    @Autowired
    private ApplicationContext applicationContext;
    private MqttServerTemplate mqttServerTemplate;

    @Override
    public void onMessage(ChannelContext context, String clientId, Message message) {
        log.info("context:{} clientId:{} message:{} payload:{}", context, clientId, message, ByteBufferUtil.toString(message.getPayload()));
//        boolean result = mqttServerTemplate.publishAll(message.getTopic(), "收到".getBytes(StandardCharsets.UTF_8));
//        log.info("Mqtt publishAll result:{}", result);
        }

    @Override
    public void afterSingletonsInstantiated() {
        // 单利 bean 初始化完成之后从 ApplicationContext 中获取 bean
        mqttServerTemplate = applicationContext.getBean(MqttServerTemplate.class);
    }
}
