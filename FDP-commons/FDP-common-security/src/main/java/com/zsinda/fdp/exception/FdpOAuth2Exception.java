package com.zsinda.fdp.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsinda.fdp.component.FdpOAuth2ExceptionJacksonSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @program: FDPlatform
 * @description: 定义自己的OAuth2Exception
 * @author: Sinda
 * @create: 2020-01-01 19:09
 */
@JsonSerialize(using = FdpOAuth2ExceptionJacksonSerializer.class)
public class FdpOAuth2Exception extends OAuth2Exception {
    @Getter
    private String errorCode;

    public FdpOAuth2Exception(String msg) {
        super(msg);
    }

    public FdpOAuth2Exception(String msg, Throwable t) {
        super(msg,t);
    }

    public FdpOAuth2Exception(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
