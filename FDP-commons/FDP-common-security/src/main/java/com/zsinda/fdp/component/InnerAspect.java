package com.zsinda.fdp.component;

import cn.hutool.core.util.StrUtil;
import com.zsinda.fdp.annotation.Inner;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.zsinda.fdp.constant.AuthorizationConstants.FROM;
import static com.zsinda.fdp.constant.AuthorizationConstants.IS_COMMING_INNER_YES;


/**
 * @program: FDPlatform
 * @description: 配合feign 服务内部只见调用不需要走网关的不用授权认证
 * @author: Sinda
 * @create: 2019-12-27 00:11
 **/
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class InnerAspect {

    private final HttpServletRequest request;

    @SneakyThrows
    @Around("@annotation(inner)")
    public Object around(ProceedingJoinPoint point, Inner inner) {
        String header = request.getHeader(FROM);
        if (inner.value() && !StrUtil.equals(IS_COMMING_INNER_YES, header)) {
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new AccessDeniedException("Access is denied");
        }
        return point.proceed();
    }

}
