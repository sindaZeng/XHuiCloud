package com.xhuicloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xhuicloud.common.security.component.XHuiOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: XHuiCloud
 * @description: UnauthorizedException
 * @author: Sinda
 * @create: 2020-01-01 19:43
 */
@JsonSerialize(using = XHuiOAuth2ExceptionJacksonSerializer.class)
public class UnauthorizedException extends XHuiOAuth2Exception {

    public UnauthorizedException(String message, Throwable throwable) {
        super(message,throwable);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
