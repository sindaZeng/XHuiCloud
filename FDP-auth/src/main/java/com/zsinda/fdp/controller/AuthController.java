package com.zsinda.fdp.controller;

import cn.hutool.core.util.StrUtil;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

//import com.zsinda.fdp.base.IDGenerate;

/**
 * @program: FDPlatform
 * @description: AuthController
 * @author: Sinda
 * @create: 2019-12-25 23:56
 **/
@Slf4j
@RestController
@RequestMapping("/token")
@AllArgsConstructor
@Api(value = "auth", tags = "认证模块")
public class AuthController {

    private final TokenStore tokenStore;

    private final ClientDetailsService clientDetailsService;

//    private final SysUserServiceFeign sysUserServiceFeign;

    private final RedisTemplate redisTemplate;

//    private final IDGenerate defaultSnowflakeIDGenerate;

    /**
     * 认证页面
     * @param modelAndView
     * @param error        表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("ftl/login");
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
            modelAndView.addObject("user", SpringSecurityUtils.getUser());
        }
        modelAndView.setViewName("ftl/confirm");
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

    @GetMapping("/test")
//    @SysLog("测试111111")
//    @FdpLock(value = "#id", isUserTryLock = true)
//    @GlobalTransactional
//    @CacheEvict(value = , allEntries = true)
    @CachePut(value = "CachePut")
    public R user(@RequestParam(value = "id") Integer id,@RequestParam(value = "amount") Integer amount) throws SQLException {
        log.info("id+{}",id);
        //        long l = defaultSnowflakeIDGenerate.get();
//        Object value = redisTemplate.opsForValue().get(id);
//        if (null==value){
//            redisTemplate.opsForValue().set(id,"123123123");
//        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        sysUserServiceFeign.user(IS_COMMING_INNER_YES);
//        return R.ok(1/0);
        return R.ok(true);
    }


}

