package com.zsinda.fdp.aspect;

import com.zsinda.fdp.annotation.ChooseDataSource;
import com.zsinda.fdp.context.DynamicDataSourceHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author X
 * @date 2019/09/03
 * 数据源AOP切面定义
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class DataSourceAspect{

    @Pointcut("execution(* com.zsinda.mapper.*.*(..))")
    public void pointcut() {
    }

    @SneakyThrows
    @Before("pointcut()")
    public void before(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        ChooseDataSource chooseDataSource = AnnotationUtils.findAnnotation(method, ChooseDataSource.class);
        if (Objects.isNull(chooseDataSource)) {
            chooseDataSource = AnnotationUtils.findAnnotation(point.getClass(), ChooseDataSource.class);
            if (Objects.isNull(chooseDataSource)) {
                Object proxy = point.getThis();
                Field h = FieldUtils.getDeclaredField(proxy.getClass().getSuperclass(), "h", true);
                AopProxy aopProxy = (AopProxy) h.get(proxy);
                ProxyFactory advised = (ProxyFactory) FieldUtils.readDeclaredField(aopProxy, "advised", true);
                Class<?> targetClass = advised.getProxiedInterfaces()[0];
                chooseDataSource = AnnotationUtils.findAnnotation(targetClass, ChooseDataSource.class);
            }
        }
        DynamicDataSourceHolder.setDataSource(Objects.nonNull(chooseDataSource) ? chooseDataSource.value(): "");
    }


    /**
     * 执行完切面后，将线程共享中的数据源名称清空
     */
    @After("pointcut()")
    public void after() {
        DynamicDataSourceHolder.removeDataSource();
    }


}