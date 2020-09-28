package com.xhuicloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xhuicloud.common.security.component.XHuiOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: XHuiCloud
 * @description: ForbiddenException
 * @author: Sinda
 * @create: 2020-01-01 19:49
 */
@JsonSerialize(using = XHuiOAuth2ExceptionJacksonSerializer.class)
public class ForbiddenException extends XHuiOAuth2Exception {

    public ForbiddenException(String msg, Throwable t) {
        super(msg,t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "access_denied";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FORBIDDEN.value();
    }

}
