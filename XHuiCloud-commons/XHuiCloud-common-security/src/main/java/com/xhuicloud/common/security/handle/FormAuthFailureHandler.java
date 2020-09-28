package com.xhuicloud.common.security.handle;

import cn.hutool.http.HttpUtil;
import com.xhuicloud.common.core.utils.WebUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

/**
 * @program: XHuiCloud
 * @description: FormAuthFailureHandler
 * @author: Sinda
 * @create: 2020-07-01 11:46
 */

@Slf4j
public class FormAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.debug("表单登录失败:{}", exception.getLocalizedMessage());
        WebUtils.getResponse().sendRedirect(String.format("/token/login?error=%s"
                , HttpUtil.encodeParams(exception.getMessage(), Charset.defaultCharset())));
    }
}
