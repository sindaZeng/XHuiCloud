/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.auth.handle;

import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.utils.WebUtils;
import com.xhuicloud.common.data.tenant.XHuiTenantThreadBroker;
import com.xhuicloud.common.security.handle.AbstractAuthenticationFailureEvenHandler;
import com.xhuicloud.common.security.service.XHuiUser;
import com.xhuicloud.logs.entity.SysLogLogin;
import com.xhuicloud.logs.feign.SysLogLoginFeign;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;

/**
 * @program: XHuiCloud
 * @description: 登录失败处理器
 * @author: Sinda
 * @create: 2020-01-11 16:46
 */
@Slf4j
@Component
@AllArgsConstructor
public class XHuiAuthFailureHandler extends AbstractAuthenticationFailureEvenHandler {

    private final SysLogLoginFeign sysLogLoginFeign;

    @Override
    public void handle(AuthenticationException authenticationException, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
        String username = (String) authentication.getPrincipal();
        SysLogLogin sysLogLogin = new SysLogLogin();
        sysLogLogin.setUsername(username);
        sysLogLogin.setUserId(null);
        sysLogLogin.setLoginTime(LocalDateTime.now());
        sysLogLogin.setIp(WebUtils.getIP(request));
        sysLogLogin.setUseragent(WebUtils.userAgent(request));
        sysLogLogin.setStatus(CommonConstants.FAIL);
        sysLogLogin.setRemake(authenticationException.getLocalizedMessage());
        sysLogLoginFeign.save(sysLogLogin, IS_COMMING_ANONYMOUS_YES);
        XHuiTenantThreadBroker.execute(() -> null,
                id -> sysLogLoginFeign.save(sysLogLogin, IS_COMMING_ANONYMOUS_YES));
    }
}
