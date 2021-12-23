package com.xhuicloud.auth.handle;

import com.google.common.collect.Maps;
import com.xhuicloud.common.security.handle.AbstractAuthenticationSuccessEventHandler;
import com.xhuicloud.common.security.service.XHuiUser;
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

    @Override
    public void handle(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户：{} 登录成功", authentication.getPrincipal());
        XHuiUser xHuiUser = (XHuiUser) authentication.getPrincipal();
        Map<String, String> params = Maps.newHashMap();
        params.put("first.DATA","");
        params.put("keyword1.DATA","");
        params.put("keyword2.DATA","");
        params.put("keyword3.DATA","");
        params.put("keyword4.DATA","");
        params.put("remark.DATA","");
        PushSingle pushSingle = WeChatMpMessage.LOGIN_SUCCESS.getPushSingle(params);
        pushSingle.setUserId(xHuiUser.getId());
        pushSingle.setPushChannelEnums(Arrays.asList(PushChannelEnum.WECHAT_MP));
        pushCommonFeign.single(pushSingle);
    }
}
