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

package com.xhuicloud.common.lock.aspect;

import com.xhuicloud.common.lock.XHuiRedissonLock;
import com.xhuicloud.common.lock.annotation.XHuiLock;
import jodd.util.StringPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @program: XHuiCloud
 * @description: LockAspect
 * @author: Sinda
 * @create: 2020-02-04 10:01
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class LockAspect {

    private final XHuiRedissonLock redissonLock;

    private final ExpressionParser PARSER = new SpelExpressionParser();


    @Around("@annotation(XHuiLock)")
    public void around(ProceedingJoinPoint joinPoint, XHuiLock XHuiLock) {
        //获取锁名称
        String lockName = getLockName(XHuiLock, joinPoint);
        //获取超时时间，默认10秒
        int leaseTime = XHuiLock.leaseTime();
        boolean lockFlag = true;
        if (!XHuiLock.isUserTryLock()) {
            redissonLock.lock(lockName, leaseTime);
        } else {
            lockFlag = redissonLock.tryLock(lockName, leaseTime, XHuiLock.waitTime());
        }
        try {
            log.info("获取Redis分布式锁[成功]，加锁完成，开始执行业务逻辑...");
            if (lockFlag) {
                joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            log.error("获取Redis分布式锁[异常]，加锁失败", throwable);
            throwable.printStackTrace();
        } finally {
            //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
            if (redissonLock.isHeldByCurrentThread(lockName)) {
                redissonLock.unlock(lockName);
            }
        }
        log.info("释放Redis分布式锁[成功]，解锁完成，结束业务逻辑...");
    }

    /**
     * 获取锁的name，如果没有指定，则按方法名大写处理
     * 支持springEl表达式
     *
     * @param XHuiLock
     * @param joinPoint
     * @return
     */
    private String getLockName(XHuiLock XHuiLock, ProceedingJoinPoint joinPoint) {
        String key = XHuiLock.value();
        String keyPrefix = XHuiLock.prefix();
        Signature signature = joinPoint.getSignature();

        String methodName = signature.getName();
        if (StringUtils.isNotEmpty(key) && key.startsWith(StringPool.HASH)) {
            Expression expression = PARSER.parseExpression(key);
            Object[] arguments = joinPoint.getArgs();
            EvaluationContext context = new StandardEvaluationContext();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] paramNames = methodSignature.getParameterNames();
            for (int i = 0; i < arguments.length; i++) {
                context.setVariable(paramNames[i], arguments[i]);
            }
            key = expression.getValue(context, String.class);
        }
        if (StringUtils.isEmpty(key)){
            key=methodName.toUpperCase();
        }
        if (StringUtils.isBlank(keyPrefix)){
            keyPrefix+=methodName.toUpperCase()+StringPool.DASH;
        }
        return keyPrefix+key;
    }

}
