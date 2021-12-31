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
