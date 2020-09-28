package com.xhuicloud.common.log.aspect;


import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.log.event.SysLogEvent;
import com.xhuicloud.common.log.utils.LogUtil;
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

import static com.xhuicloud.common.core.constant.CommonConstants.LOG_EX_TYPE;

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
        com.xhuicloud.logs.entity.SysLog log = LogUtil.getSysLog();
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

    public void doAfterThrowable(Throwable e, com.xhuicloud.logs.entity.SysLog log) {
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
