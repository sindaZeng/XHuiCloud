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

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Maps;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.utils.WebUtils;
import com.xhuicloud.push.common.PushSingle;
import com.xhuicloud.push.enums.PushChannelEnum;
import com.xhuicloud.push.enums.WeChatMpMessage;
import com.xhuicloud.push.feign.PushCommonFeign;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;

@Component
@AllArgsConstructor
public class NoticeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final PushCommonFeign pushCommonFeign;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AccessTokenAuthenticationToken) {
            OAuth2AccessTokenAuthenticationToken authenticationToken = (OAuth2AccessTokenAuthenticationToken) authentication;
            Map<String, Object> additionalParameters = authenticationToken.getAdditionalParameters();
            String username = (String) additionalParameters.get(IdTokenClaimNames.SUB);
            String tenantName = (String) additionalParameters.get(CommonConstants.TENANT_NAME);
            Long userId = (Long) additionalParameters.get(CommonConstants.USER_ID);
            Long tenantId = (Long) additionalParameters.get(CommonConstants.TENANT_ID);
            Map<String, String> params = Maps.newHashMap();
            params.put("first.DATA", "账号名密码登录");
            params.put("keyword1.DATA", username);
            params.put("keyword2.DATA",tenantName);
            params.put("keyword3.DATA", DateUtil.now());
            params.put("keyword4.DATA", WebUtils.getIP(request));
            params.put("remark.DATA", "非本人登录请修改密码!");
            PushSingle pushSingle = WeChatMpMessage.LOGIN_SUCCESS.getPushSingle(params);
            pushSingle.setUserId(userId);
            pushSingle.setTenantId(tenantId);
            pushSingle.setPushChannelEnums(Arrays.asList(PushChannelEnum.WECHAT_MP));
            pushCommonFeign.single(pushSingle, IS_COMMING_ANONYMOUS_YES);
        }
    }
}
