package com.xhuicloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xhuicloud.common.security.component.XHuiOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: XHuiCloud
 * @description: ServerErrorException
 * @author: Sinda
 * @create: 2020-01-01 19:54
 */
@JsonSerialize(using = XHuiOAuth2ExceptionJacksonSerializer.class)
public class ServerErrorException extends XHuiOAuth2Exception {
    public ServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "server_error";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

}
