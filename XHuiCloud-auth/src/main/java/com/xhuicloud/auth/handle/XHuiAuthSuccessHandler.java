package com.xhuicloud.auth.handle;

import cn.hutool.core.date.DateUtil;
import com.alibaba.nacos.common.utils.IPUtil;
import com.google.common.collect.Maps;
import com.xhuicloud.common.core.utils.WebUtils;
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

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

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
        try {

        XHuiUser xHuiUser = (XHuiUser) authentication.getPrincipal();
        Map<String, String> params = Maps.newHashMap();
        params.put("first.DATA","账号名密码登录");
        params.put("keyword1.DATA",xHuiUser.getUsername());
        params.put("keyword2.DATA",xHuiUser.getTenantName());
        params.put("keyword3.DATA", DateUtil.now());
        params.put("keyword4.DATA", WebUtils.getIP(request));
        params.put("remark.DATA","非本人登录请修改密码!");
        PushSingle pushSingle = WeChatMpMessage.LOGIN_SUCCESS.getPushSingle(params);
        pushSingle.setUserId(xHuiUser.getId());
        pushSingle.setTenantId(xHuiUser.getTenantId());
        pushSingle.setPushChannelEnums(Arrays.asList(PushChannelEnum.WECHAT_MP));
        pushCommonFeign.single(pushSingle, IS_COMMING_INNER_YES);
        }catch (Exception e) {
            log.info("用户：{} 登录成功推送失败", e);
        }
    }

}
