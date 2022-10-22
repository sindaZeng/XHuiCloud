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
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUser;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.upms.feign.SysTenantServiceFeign;
import com.xhuicloud.upms.vo.TenantVo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;

/**
 * @program: XHuiCloud
 * @description: AuthController
 * @author: Sinda
 * @create: 2019-12-25 23:56
 **/
@Slf4j
@RestController
@RequestMapping("/authorize")
@AllArgsConstructor
@Api(value = "authorize", tags = "认证模块")
public class AuthTokenEndpoint {

    private final RegisteredClientRepository registeredClientRepository;

    private final SysTenantServiceFeign sysTenantServiceFeign;

    private final OAuth2AuthorizationService authorizationService;

    private final CacheManager cacheManager;
    /**
     * 认证页面
     *
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
     *
     * @param modelAndView
     * @param errorCode    错误码
     * @param description  错误描述
     * @param uri          参考地址
     * @return ModelAndView
     */
    @GetMapping("/error")
    public ModelAndView nomatch(ModelAndView modelAndView,
                                @RequestParam(required = false) String errorCode,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) String uri) {
        modelAndView.setViewName("ftl/error");
        modelAndView.addObject("errorCode", errorCode);
        modelAndView.addObject("description", description);
        modelAndView.addObject("uri", uri);
        return modelAndView;
    }

    /**
     * 确认授权页面
     *
     * @return
     */
    @GetMapping("/confirm_access")
    public ModelAndView confirm(ModelAndView modelAndView,
                                @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                @RequestParam(OAuth2ParameterNames.STATE) String state) {

        modelAndView.addObject("scopeList", StringUtils.commaDelimitedListToSet(scope));
        modelAndView.addObject("state", state);
        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        modelAndView.addObject("app", client);
        modelAndView.addObject("user", SecurityHolder.getUser());
        modelAndView.setViewName("ftl/confirm");
        return modelAndView;
    }


    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Response logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if (StrUtil.isBlank(authorization)) {
            return Response.success();
        }
        String tokenValue = authorization.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StrUtil.EMPTY).trim();
        OAuth2Authorization accessToken = authorizationService.findByToken(tokenValue, OAuth2TokenType.ACCESS_TOKEN);

        if (accessToken == null || StrUtil.isBlank(accessToken.getAccessToken().getToken().getTokenValue())) {
            return Response.success();
        }
        cacheManager.getCache(CacheConstants.SYS_USER).evict(accessToken.getPrincipalName());
        // 清空access token
        authorizationService.remove(accessToken);
        return Response.success();
    }

}

