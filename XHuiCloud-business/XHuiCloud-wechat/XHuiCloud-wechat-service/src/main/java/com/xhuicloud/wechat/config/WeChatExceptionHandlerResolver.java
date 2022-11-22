package com.xhuicloud.wechat.config;

import com.xhuicloud.common.authorization.resource.component.GlobalExceptionHandlerResolver;
import com.xhuicloud.common.core.utils.Response;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/22
 */
@Slf4j
@RestControllerAdvice
public class WeChatExceptionHandlerResolver extends GlobalExceptionHandlerResolver {

    /**
     * 微信异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(WxErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleGlobalException(WxErrorException e) {
        log.error("微信异常 ex={}", e.getMessage(), e);
        return Response.failed(e.getError().getErrorMsg());
    }

}
