package com.xhuicloud.upms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.upms.service.SysMobileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: XHuiCloud
 * @description: MobileServiceImpl
 * @author: Sinda
 * @create: 2019-12-26 23:37
 **/
@Slf4j
@Service
@AllArgsConstructor
public class SysMobileServiceImpl implements SysMobileService {

    private final RedisTemplate redisTemplate;

    /**
     * 发送手机验证码
     * @param mobile mobile
     * @return code
     */
    @Override
    public Response<Boolean> sendSmsCode(String mobile) {

        Object codeObj = redisTemplate.opsForValue().get(SecurityConstants.CODE_KEY + mobile);
        if (codeObj != null) {
            log.info("验证码未过期:{}，{}", mobile, codeObj);
            return Response.success(Boolean.FALSE, "验证码发送过频繁");
        }

        String code = RandomUtil.randomNumbers(6);
        log.info("手机号生成验证码成功:{},{}", mobile, code);
        redisTemplate.opsForValue().set(
                SecurityConstants.CODE_KEY + mobile
                , code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
        return Response.success(Boolean.TRUE);
    }
}
