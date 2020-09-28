package com.xhuicloud.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: XHuiCloud
 * @description: state
 * @author: Sinda
 * @create: 2020-05-27 18:15
 */
@Getter
@AllArgsConstructor
public enum AuditStateEnum {

    DISABLE(0, "禁用"),
    NORMAL(1, "正常"),
    TOAUDIT(2, "待审核"),
    REFUSE(3, "拒绝");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String description;
}
