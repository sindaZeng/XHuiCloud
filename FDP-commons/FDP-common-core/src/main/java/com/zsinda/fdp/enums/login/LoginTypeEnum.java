package com.zsinda.fdp.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: FDPlatform
 * @description: LoginTypeEnum
 * @author: Sinda
 * @create: 2020-06-17 10:33
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 账号密码登录
     */
    PWD("PWD", "账号密码登录"),

    /**
     * 验证码登录
     */
    SMS("SMS", "验证码登录"),

    /**
     * QQ登录
     */
    QQ("QQ", "QQ登录"),

    /**
     * 微信登录
     */
    WECHAT("WX", "微信登录");

    /**
     * 类型
     */
    private String type;
    /**
     * 描述
     */
    private String description;
}
