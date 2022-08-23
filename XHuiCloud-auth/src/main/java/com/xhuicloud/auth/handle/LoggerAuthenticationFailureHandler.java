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
import com.xhuicloud.logs.entity.SysLogLogin;
import com.xhuicloud.logs.feign.SysLogLoginFeign;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;

@Component
@AllArgsConstructor
public class LoggerAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final SysLogLoginFeign sysLogLoginFeign;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter(OAuth2ParameterNames.USERNAME);
        SysLogLogin sysLogLogin = new SysLogLogin();
        sysLogLogin.setUsername(username);
        sysLogLogin.setLoginTime(LocalDateTime.now());
        sysLogLogin.setIp(WebUtils.getIP(request));
        sysLogLogin.setUseragent(WebUtils.userAgent(request));
        sysLogLogin.setStatus(CommonConstants.FAIL);
        sysLogLogin.setRemake(exception.getMessage());
        sysLogLoginFeign.save(sysLogLogin, IS_COMMING_ANONYMOUS_YES);
    }
}
