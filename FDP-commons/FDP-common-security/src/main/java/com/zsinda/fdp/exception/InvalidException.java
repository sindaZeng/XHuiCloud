package com.zsinda.fdp.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsinda.fdp.component.FdpOAuth2ExceptionJacksonSerializer;

/**
 * @program: FDPlatform
 * @description: InvalidException
 * @author: Sinda
 * @create: 2020-01-01 19:48
 */
@JsonSerialize(using = FdpOAuth2ExceptionJacksonSerializer.class)
public class InvalidException extends FdpOAuth2Exception {

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
