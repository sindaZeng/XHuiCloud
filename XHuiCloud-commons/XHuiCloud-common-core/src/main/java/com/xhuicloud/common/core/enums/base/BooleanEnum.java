package com.xhuicloud.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/25 4:00 下午
 */
@Getter
@AllArgsConstructor
public enum BooleanEnum {


    NO(0, "否"),
    YES(1, "是");

    /**
     * 类型
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String description;
}
