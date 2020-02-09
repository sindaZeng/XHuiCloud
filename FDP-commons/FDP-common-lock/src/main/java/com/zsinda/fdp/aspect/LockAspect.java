package com.zsinda.fdp.aspect;

import com.zsinda.fdp.FdpRedissonLock;
import com.zsinda.fdp.annotation.FdpLock;
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
 * @program: FDPlatform
 * @description: LockAspect
 * @author: Sinda
 * @create: 2020-02-04 10:01
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class LockAspect {

    private final FdpRedissonLock redissonLock;

    private final ExpressionParser PARSER = new SpelExpressionParser();


    @Around("@annotation(fdpLock)")
    public void around(ProceedingJoinPoint joinPoint, FdpLock fdpLock) {
        //获取锁名称
        String lockName = getLockName(fdpLock, joinPoint);
        //获取超时时间，默认10秒
        int leaseTime = fdpLock.leaseTime();
        boolean lockFlag = true;
        if (!fdpLock.isUserTryLock()) {
            redissonLock.lock(lockName, leaseTime);
        } else {
            lockFlag = redissonLock.tryLock(lockName, leaseTime, 30L);
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
     * @param fdpLock
     * @param joinPoint
     * @return
     */
    private String getLockName(FdpLock fdpLock, ProceedingJoinPoint joinPoint) {
        String key = fdpLock.value();
        String keyPrefix = fdpLock.prefix();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        if (StringUtils.isNotEmpty(key) && key.startsWith("#")) {
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
            keyPrefix+=methodName.toUpperCase()+"-";
        }
        return keyPrefix+key;
    }

}
