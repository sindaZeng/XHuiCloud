package com.xhuicloud.push.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushChannelEnum {

    SMS("短信"),

    WECHAT_MP("微信公众号"),
    ;

    /**
     * 类型描述
     */
    String message;

}
