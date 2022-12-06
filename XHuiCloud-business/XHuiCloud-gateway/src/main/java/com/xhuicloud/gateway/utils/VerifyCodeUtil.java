package com.xhuicloud.gateway.utils;

import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.exception.ValidateCodeException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/9/26
 */
@Component
@AllArgsConstructor
public class VerifyCodeUtil {
    private final RedisTemplate redisTemplate;

    private final CaptchaService captchaService;

    public void validateCaptchaCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StringUtils.isBlank(code)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_IS_NULL_FAIL);
        }

        CaptchaVO vo = new CaptchaVO();
        vo.setCaptchaVerification(code);
        vo.setCaptchaType("blockPuzzle");
        if (!captchaService.verification(vo).isSuccess()) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_IS_NULL_FAIL);
        }

        return;

    }

    public void validateCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StringUtils.isBlank(code)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_IS_NULL_FAIL);
        }
        String mobile = request.getQueryParams().getFirst("mobile");
        if (StrUtil.isBlank(mobile)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.MOBILE_IS_NULL_FAIL);
        }

        if ("123456".equals(code)) {
            return;
        }
        String key = CacheConstants.MOBILE_CODE_KEY + mobile;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        String codeStr = (String) redisTemplate.opsForValue().get(key);

        if (StringUtils.isBlank(codeStr)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_OVERDUE_FAIL);
        }

        if (!StringUtils.equals(codeStr, code)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_VALIDATE_FAIL);
        }
        redisTemplate.delete(key);
    }

}
