package com.xhuicloud.common.mq.aspect;

import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.mq.entity.MqEntity;
import com.xhuicloud.common.mq.service.CommonMqService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@AllArgsConstructor
public class MqListenerAop {

    private final CommonMqService commonMqService;

    @Around("@annotation(rabbitListener)")
    public Object around(ProceedingJoinPoint point, RabbitListener rabbitListener) {
        String queueName = rabbitListener.queues()[0];
        MqEntity entity = (MqEntity) point.getArgs()[0];
        String classMethodName = getClassMethodName(point);
        log.info("消费者{}接收到消息{}", classMethodName, entity);
        try {
            point.proceed();
        } catch (SysException e) {
            log.error("消费者{}发生异常{}。接收到消息{}", classMethodName, e.getMessage(), entity);
            commonMqService.sendError(queueName, entity, e);
        } catch (Throwable e) {
            log.error("消费者{}发生异常{}。接收到消息{}", classMethodName, e.getMessage(), entity);
            commonMqService.sendError(queueName, entity, e);
        }
        return null;
    }

    /**
     * 获取类方法全名
     *
     * @param point
     * @return
     */
    private String getClassMethodName(ProceedingJoinPoint point) {
        try {
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Object target = point.getTarget();
            Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            String methodName = method.getName();
            String className = point.getTarget().getClass().getName();
            return className + "#" + methodName;
        } catch (Exception e) {
            log.error("MQ拦截获取类方法名称异常", e);
        }
        return null;
    }
}
