package com.xhuicloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xhuicloud.common.security.component.XHuiOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: XHuiCloud
 * @description: MethodNotAllowedException
 * @author: Sinda
 * @create: 2020-01-01 19:51
 */
@JsonSerialize(using = XHuiOAuth2ExceptionJacksonSerializer.class)
public class MethodNotAllowedException extends XHuiOAuth2Exception {

    public MethodNotAllowedException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "method_not_allowed";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }

}
