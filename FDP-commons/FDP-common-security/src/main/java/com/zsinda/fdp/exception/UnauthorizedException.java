package com.zsinda.fdp.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsinda.fdp.component.FdpOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: FDPlatform
 * @description: UnauthorizedException
 * @author: Sinda
 * @create: 2020-01-01 19:43
 */
@JsonSerialize(using = FdpOAuth2ExceptionJacksonSerializer.class)
public class UnauthorizedException extends FdpOAuth2Exception {

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
