package com.zsinda.fdp.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.entity.SysUser;
import com.zsinda.fdp.mapper.SysUserMapper;
import com.zsinda.fdp.service.MobileService;
import com.zsinda.fdp.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class MobileServiceImpl implements MobileService {
    private static final Object CODE_KEY = "FDP_SMS_CODE_KEY";
    private static final long CODE_TIME = 60;
    private final RedisTemplate redisTemplate;
    private final SysUserMapper userMapper;


    /**
     * 发送手机验证码
     * @param mobile mobile
     * @return code
     */
    @Override
    public R<Boolean> sendSmsCode(String mobile) {
        List<SysUser> userList = userMapper.selectList(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, mobile));

        if (CollUtil.isEmpty(userList)) {
            log.info("手机号未注册:{}", mobile);
            return R.ok(Boolean.FALSE, "手机号未注册");
        }

        Object codeObj = redisTemplate.opsForValue().get(CODE_KEY + mobile);

        if (codeObj != null) {
            log.info("手机号验证码未过期:{}，{}", mobile, codeObj);
            return R.ok(Boolean.FALSE, "验证码发送过频繁");
        }

        String code = RandomUtil.randomNumbers(4);
        log.debug("手机号生成验证码成功:{},{}", mobile, code);
        redisTemplate.opsForValue().set(
                CODE_KEY + mobile
                , code, CODE_TIME, TimeUnit.SECONDS);
        return R.ok(Boolean.TRUE, code);
    }
}
