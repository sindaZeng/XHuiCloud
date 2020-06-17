package com.zsinda.fdp.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zsinda.fdp.constant.SecurityConstants;
import com.zsinda.fdp.service.SysMobileService;
import com.zsinda.fdp.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: FDPlatform
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
    public R<Boolean> sendSmsCode(String mobile) {

        Object codeObj = redisTemplate.opsForValue().get(SecurityConstants.CODE_KEY + mobile);
        if (codeObj != null) {
            log.info("验证码未过期:{}，{}", mobile, codeObj);
            return R.ok(Boolean.FALSE, "验证码发送过频繁");
        }

        String code = RandomUtil.randomNumbers(6);
        log.info("手机号生成验证码成功:{},{}", mobile, code);
        redisTemplate.opsForValue().set(
                SecurityConstants.CODE_KEY + mobile
                , code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
        return R.ok(Boolean.TRUE);
    }
}
