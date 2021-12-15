package com.xhuicloud.common.security.handle;

import cn.hutool.extra.spring.SpringUtil;
import com.xhuicloud.common.core.utils.KeyStrResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 避免回调丢失租户
 */
@Slf4j
public class XHuiSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    public XHuiSimpleUrlAuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
        }

        if (isAlwaysUseDefaultTargetUrl()) {
            this.requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
        }
        else {
            this.clearAuthenticationAttributes(request);
            assert savedRequest != null;
            String targetUrl = savedRequest.getRedirectUrl() + "&tenant_id="
                    + SpringUtil.getBean(KeyStrResolver.class).key();
            this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }

}
