package com.xhuicloud.common.security.handle;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.xhuicloud.common.core.constant.CommonConstants.REDIRECT_URL;

public class XHuiLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException {
        String redirectUrl = httpServletRequest.getParameter(REDIRECT_URL);
        String referer = httpServletRequest.getHeader(HttpHeaders.REFERER);

        if (StrUtil.isNotBlank(redirectUrl)) {
            httpServletResponse.sendRedirect(redirectUrl);
        }
        else if (StrUtil.isNotBlank(referer)) {
            httpServletResponse.sendRedirect(referer);
        }
    }
}
