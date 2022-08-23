package com.xhuicloud.common.sentinel.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Desc 全局异常增强
 * @Author Sinda
 * @Date 2022/8/18
 */
public class EnhanceExceptionHandler implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ControllerAdvice controllerAdvice = bean.getClass().getAnnotation(ControllerAdvice.class);
        RestControllerAdvice restControllerAdvice = bean.getClass().getAnnotation(RestControllerAdvice.class);
        if (controllerAdvice != null || restControllerAdvice != null) {
            return new EnhanceGlobalExceptionResolver(bean).getProxyInstance();
        }
        return bean;
    }

}
