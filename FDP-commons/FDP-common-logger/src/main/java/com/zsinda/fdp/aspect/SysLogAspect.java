package com.zsinda.fdp.aspect;


import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.event.SysLogEvent;
import com.zsinda.fdp.util.LogUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static com.zsinda.fdp.constant.CommonConstants.LOG_EX_TYPE;

/**
 * 操作日志使用spring event异步入库
 *
 * @author sinda
 * @date 2020年01月31日20:42:36
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class SysLogAspect {

    private final ApplicationEventPublisher publisher;


    @SneakyThrows
    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        com.zsinda.fdp.entity.SysLog log = LogUtil.getSysLog();
        log.setClassPath(point.getTarget().getClass().getName());
        log.setRequestMethod(point.getSignature().getName());
        log.setDescription(sysLog.value());
        // 参数
        Object[] args = point.getArgs();
        log.setParams(Arrays.toString(args));
        // 发送异步日志事件
        try {
            Object obj = point.proceed();
            log.setFinishTime(LocalDateTime.now());
            log.setTime(log.getCreateTime().until(log.getFinishTime(), ChronoUnit.MILLIS) + "");
            publisher.publishEvent(new SysLogEvent(log));
            return obj;
        } catch (Throwable e) {
            doAfterThrowable(e,log);
            throw e;
        }
    }

    public void doAfterThrowable(Throwable e, com.zsinda.fdp.entity.SysLog log) {
        log.setType(LOG_EX_TYPE);
        // 异常对象
        log.setExDetail(LogUtil.getStackTrace(e));
        // 异常信息
        log.setExDesc(e.getMessage());
        log.setFinishTime(LocalDateTime.now());
        log.setTime(log.getCreateTime().until(log.getFinishTime(), ChronoUnit.MILLIS) + "");
        publisher.publishEvent(new SysLogEvent(log));
    }


}
