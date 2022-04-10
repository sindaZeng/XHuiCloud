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

package com.xhuicloud.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.security.utils.SecurityHolder;
import com.xhuicloud.upms.feign.SysTenantServiceFeign;
import com.xhuicloud.upms.vo.TenantVo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;

/**
 * @program: XHuiCloud
 * @description: AuthController
 * @author: Sinda
 * @create: 2019-12-25 23:56
 **/
@Slf4j
@RestController
@RequestMapping("/oauth2")
@AllArgsConstructor
@Api(value = "oauth2", tags = "认证模块")
public class AuthTokenEndpoint {

    private final TokenStore tokenStore;

    private final ClientDetailsService clientDetailsService;

    private final SysTenantServiceFeign sysTenantServiceFeign;

    /**
     * 认证页面
     * @param modelAndView
     * @param error        表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        List<TenantVo> list = sysTenantServiceFeign.list(IS_COMMING_ANONYMOUS_YES).getData();
        modelAndView.setViewName("ftl/login");
        modelAndView.addObject("error", error);
        modelAndView.addObject("tenants", list);
        return modelAndView;
    }

    /**
     * 回调域错误页
     * @param modelAndView
     * @param error        回调域错 错误信息
     * @return ModelAndView
     */
    @GetMapping("/notmatch")
    public ModelAndView nomatch(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("ftl/notmatch");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    /**
     * 确认授权页面
     *
     * @param request
     * @param session
     * @param modelAndView
     * @return
     */
    @GetMapping("/confirm_access")
    public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
        Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
        modelAndView.addObject("scopeList", scopeList.keySet());

        Object auth = session.getAttribute("authorizationRequest");
        if (auth != null) {
            AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
            modelAndView.addObject("app", clientDetails.getAdditionalInformation());
            modelAndView.addObject("permission", clientDetails.getAdditionalInformation().get("permission"));
            modelAndView.addObject("user", SecurityHolder.getUser());
        }
        modelAndView.setViewName("ftl/confirm");
        return modelAndView;
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Response logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if (StrUtil.isBlank(authorization)) {
            return Response.success(Boolean.FALSE, "退出失败,未找到此token!");
        }
        String tokenValue = authorization.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();

        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
            return Response.success(Boolean.TRUE, "退出失败,未找到此token!");
        }
        // 清空access token
        tokenStore.removeAccessToken(accessToken);
        // 清空 refresh token
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        if (refreshToken != null) {
            tokenStore.removeRefreshToken(refreshToken);
        }
        return Response.success(Boolean.TRUE);
    }

}

