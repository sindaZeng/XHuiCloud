package com.xhuicloud.common.mqtt.server.auth;

import net.dreamlu.iot.mqtt.core.server.auth.IMqttServerUniqueIdService;
import org.springframework.context.annotation.Configuration;
import org.tio.core.ChannelContext;

/**
 * @Desc 自定义 clientId，请按照自己的需求和业务进行扩展
 * @Author Sinda
 * @Date 2023/2/14
 */
@Configuration(proxyBeanMethods = false)
public class MqttUniqueIdService implements IMqttServerUniqueIdService {

    @Override
    public String getUniqueId(ChannelContext context, String clientId, String userName, String password) {
        // 返回的 uniqueId 会替代 mqtt client 传过来的 clientId，请保证返回的 uniqueId 唯一。
        return clientId;
    }

}