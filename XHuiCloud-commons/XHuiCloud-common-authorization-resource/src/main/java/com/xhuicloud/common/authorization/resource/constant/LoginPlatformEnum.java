package com.xhuicloud.common.authorization.resource.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/9/15
 */
@Getter
@AllArgsConstructor
public enum LoginPlatformEnum {


    QQ("qq", "社交登录-QQ登录"),
    WECHAT_MP("wechat_mp", "社交登录-微信公众号登录");

    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String description;

    public static Boolean hasType(String type){
        return Arrays.stream(values()).anyMatch(x -> x.getType().equals(type));
    }
}
