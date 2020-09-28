package com.xhuicloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xhuicloud.common.security.component.XHuiOAuth2ExceptionJacksonSerializer;

/**
 * @program: XHuiCloud
 * @description: InvalidException
 * @author: Sinda
 * @create: 2020-01-01 19:48
 */
@JsonSerialize(using = XHuiOAuth2ExceptionJacksonSerializer.class)
public class InvalidException extends XHuiOAuth2Exception {

    public InvalidException(String message, Throwable throwable) {
        super(message);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_exception";
    }

    @Override
    public int getHttpErrorCode() {
        return 426;
    }
}
