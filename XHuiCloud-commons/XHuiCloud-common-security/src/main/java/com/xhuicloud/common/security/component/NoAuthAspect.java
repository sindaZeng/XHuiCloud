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

package com.xhuicloud.common.security.component;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.security.annotation.NoAuth;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;
import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

/**
 * @program: XHuiCloud
 * @description: 配合feign 服务内部只见调用不需要走网关的不用授权认证
 * @author: Sinda
 * @create: 2019-12-27 00:11
 **/
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class NoAuthAspect {

    private final HttpServletRequest request;

    @SneakyThrows
    @Around("@within(noAuth) ||@annotation(noAuth)")
    public Object around(ProceedingJoinPoint point, NoAuth noAuth) {
        if (noAuth == null) {
            Class<?> aClass = point.getTarget().getClass();
            noAuth = AnnotationUtils.findAnnotation(aClass, NoAuth.class);
        }

        String header = request.getHeader(FROM);
        if (noAuth.value() && !StrUtil.equals(IS_COMMING_INNER_YES, header)) {
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new AccessDeniedException("Access is denied");
        }
        return point.proceed();
    }

}
