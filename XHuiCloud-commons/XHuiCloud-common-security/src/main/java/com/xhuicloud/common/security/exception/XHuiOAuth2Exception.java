package com.xhuicloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xhuicloud.common.security.component.XHuiOAuth2ExceptionJacksonSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @program: XHuiCloud
 * @description: 定义自己的OAuth2Exception
 * @author: Sinda
 * @create: 2020-01-01 19:09
 */
@JsonSerialize(using = XHuiOAuth2ExceptionJacksonSerializer.class)
public class XHuiOAuth2Exception extends OAuth2Exception {
    @Getter
    private String errorCode;

    public XHuiOAuth2Exception(String msg) {
        super(msg);
    }

    public XHuiOAuth2Exception(String msg, Throwable t) {
        super(msg,t);
    }

    public XHuiOAuth2Exception(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
