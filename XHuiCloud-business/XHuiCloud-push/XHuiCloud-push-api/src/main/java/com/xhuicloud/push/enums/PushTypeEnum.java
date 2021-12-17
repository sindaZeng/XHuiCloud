package com.xhuicloud.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushTypeEnum {

    SMS(SmsMessage.class),

    WECHAT_MP(WeChatMpMessage.class),

    ;

    /**
     * 指定消息类
     */
    private final Class<? extends BasePushMessage> messageClass;

}
