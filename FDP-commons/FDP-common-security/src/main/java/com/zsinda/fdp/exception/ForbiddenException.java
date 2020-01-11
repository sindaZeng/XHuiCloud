package com.zsinda.fdp.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsinda.fdp.component.FdpOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: FDPlatform
 * @description: ForbiddenException
 * @author: Sinda
 * @create: 2020-01-01 19:49
 */
@JsonSerialize(using = FdpOAuth2ExceptionJacksonSerializer.class)
public class ForbiddenException extends FdpOAuth2Exception {

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
