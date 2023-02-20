package com.xhuicloud.ai.socket;

import cn.hutool.json.JSONUtil;
import com.xhuicloud.ai.chatgpt.client.impl.OpenAiService;
import com.xhuicloud.ai.payload.MessagePayload;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.codec.ByteBufferUtil;
import net.dreamlu.iot.mqtt.core.server.event.IMqttMessageListener;
import net.dreamlu.iot.mqtt.core.server.model.Message;
import net.dreamlu.iot.mqtt.spring.server.MqttServerTemplate;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;

import javax.annotation.Resource;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/16
 */

@Slf4j
//@Primary
@Service
public class MqttServerMessageListener implements IMqttMessageListener, SmartInitializingSingleton {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private OpenAiService openAiService;
    private MqttServerTemplate mqttServerTemplate;

    @Override
    public void onMessage(ChannelContext context, String clientId, Message message) {
        String payloadStr = ByteBufferUtil.toString(message.getPayload());
        log.info("context:{} clientId:{} message:{} payload:{}", context, clientId, message, payloadStr);
        MessagePayload messagePayload = JSONUtil.toBean(payloadStr, MessagePayload.class);
        openAiService.createCompletionForStream(mqttServerTemplate, messagePayload, message.getTopic(), context);
//        boolean result = mqttServerTemplate.publishAll(message.getTopic(), "收到".getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void afterSingletonsInstantiated() {
        // 单利 bean 初始化完成之后从 ApplicationContext 中获取 bean
        mqttServerTemplate = applicationContext.getBean(MqttServerTemplate.class);
    }
}