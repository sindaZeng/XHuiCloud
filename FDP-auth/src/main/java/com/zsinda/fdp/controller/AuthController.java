package com.zsinda.fdp.controller;

import cn.hutool.core.util.StrUtil;
import com.zsinda.fdp.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: FDPlatform
 * @description: AuthController
 * @author: Sinda
 * @create: 2019-12-25 23:56
 **/
@RestController
@RequestMapping("/token")
@Slf4j
public class AuthController {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 认证页面
     * @param modelAndView
     * @param error        表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/toLogin")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("ftl/login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }


    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if (StrUtil.isBlank(authorization)) {
            return R.ok(Boolean.FALSE, "退出失败,未找到此token!");
        }

        String tokenValue = authorization.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();

        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
            return R.ok(Boolean.TRUE, "退出失败,未找到此token!");
        }
        // 清空access token
        tokenStore.removeAccessToken(accessToken);

        // 清空 refresh token
        tokenStore.removeRefreshToken(accessToken.getRefreshToken());

        return R.ok(Boolean.TRUE);
    }


}

