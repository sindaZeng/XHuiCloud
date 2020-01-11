package com.zsinda.fdp.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsinda.fdp.component.FdpOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: FDPlatform
 * @description: MethodNotAllowedException
 * @author: Sinda
 * @create: 2020-01-01 19:51
 */
@JsonSerialize(using = FdpOAuth2ExceptionJacksonSerializer.class)
public class MethodNotAllowedException extends FdpOAuth2Exception{

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
