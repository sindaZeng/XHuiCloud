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
import java.time.LocalDateTime;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Maps;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.utils.WebUtils;
import com.xhuicloud.common.data.tenant.XHuiCommonThreadLocalHolder;
import com.xhuicloud.common.data.tenant.XHuiTenantThreadBroker;
import com.xhuicloud.common.security.handle.AbstractAuthenticationSuccessEventHandler;
import com.xhuicloud.common.security.service.XHuiUser;
import com.xhuicloud.logs.entity.SysLogLogin;
import com.xhuicloud.logs.feign.SysLogLoginFeign;
import com.xhuicloud.push.common.PushSingle;
import com.xhuicloud.push.enums.PushChannelEnum;
import com.xhuicloud.push.enums.WeChatMpMessage;
import com.xhuicloud.push.feign.PushCommonFeign;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;

/**
 * @program: XHuiCloud
 * @description: 登录成功处理器
 * @author: Sinda
 * @create: 2020-01-11 16:47
 */
@Slf4j
@Component
@AllArgsConstructor
public class XHuiAuthSuccessHandler extends AbstractAuthenticationSuccessEventHandler {

    private final PushCommonFeign pushCommonFeign;

    private final SysLogLoginFeign sysLogLoginFeign;

    @Override
    public void handle(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户：{} 登录成功", authentication.getPrincipal());
        XHuiUser xHuiUser = (XHuiUser) authentication.getPrincipal();
        Map<String, String> params = Maps.newHashMap();
        params.put("first.DATA", "账号名密码登录");
        params.put("keyword1.DATA", xHuiUser.getUsername());
        params.put("keyword2.DATA", xHuiUser.getTenantName());
        params.put("keyword3.DATA", DateUtil.now());
        params.put("keyword4.DATA", WebUtils.getIP(request));
        params.put("remark.DATA", "非本人登录请修改密码!");
        PushSingle pushSingle = WeChatMpMessage.LOGIN_SUCCESS.getPushSingle(params);
        pushSingle.setUserId(xHuiUser.getId());
        pushSingle.setTenantId(xHuiUser.getTenantId());
        pushSingle.setPushChannelEnums(Arrays.asList(PushChannelEnum.WECHAT_MP));
        XHuiTenantThreadBroker.execute(() -> xHuiUser.getTenantId(),
                id -> pushCommonFeign.single(pushSingle, IS_COMMING_ANONYMOUS_YES));
        SysLogLogin sysLogLogin = new SysLogLogin();
        sysLogLogin.setUsername(xHuiUser.getUsername());
        sysLogLogin.setUserId(xHuiUser.getId());
        sysLogLogin.setLoginTime(LocalDateTime.now());
        sysLogLogin.setIp(WebUtils.getIP(request));
        sysLogLogin.setUseragent(WebUtils.userAgent(request));
        sysLogLogin.setStatus(CommonConstants.SUCCESS);
        XHuiTenantThreadBroker.execute(() -> xHuiUser.getTenantId(),
                id -> sysLogLoginFeign.save(sysLogLogin, IS_COMMING_ANONYMOUS_YES));
    }

}
