package com.xhuicloud.common.log.component;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/1/30
 */
@Data
@AllArgsConstructor
public class MethodResult {

    private boolean success;

    private Throwable throwable;

    private String errorMsg;
}
