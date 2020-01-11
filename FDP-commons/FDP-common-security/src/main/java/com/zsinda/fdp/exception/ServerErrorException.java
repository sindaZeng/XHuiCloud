package com.zsinda.fdp.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsinda.fdp.component.FdpOAuth2ExceptionJacksonSerializer;
import org.springframework.http.HttpStatus;

/**
 * @program: FDPlatform
 * @description: ServerErrorException
 * @author: Sinda
 * @create: 2020-01-01 19:54
 */
@JsonSerialize(using = FdpOAuth2ExceptionJacksonSerializer.class)
public class ServerErrorException extends FdpOAuth2Exception{
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
