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

package com.xhuicloud.upms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.xhuicloud.common.core.constant.CacheConstants;
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
     *
     * @param mobile mobile
     * @return code
     */
    @Override
    public Response<Boolean> sendSmsCode(String mobile) {

        Object codeObj = redisTemplate.opsForValue().get(CacheConstants.MOBILE_CODE_KEY + mobile);
        if (codeObj != null) {
            log.info("验证码未过期:{}，{}", mobile, codeObj);
            return Response.success(Boolean.FALSE, "验证码发送过频繁");
        }

        String code = RandomUtil.randomNumbers(6);
        log.info("手机号生成验证码成功:{},{}", mobile, code);
        redisTemplate.opsForValue().set(
                CacheConstants.MOBILE_CODE_KEY + mobile
                , code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
        return Response.success(Boolean.TRUE);
    }
}
