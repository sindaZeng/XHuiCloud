package com.xhuicloud.common.sentinel.extension;


import com.alibaba.csp.sentinel.Tracer;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/8/19
 */
public class EnhanceGlobalExceptionResolver implements MethodInterceptor {
    private Object target;

    public EnhanceGlobalExceptionResolver(Object target) {
        this.target = target;
    }

    /**
     * 创建代理对象
     *
     * @return
     */
    public Object getProxyInstance() {
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(this);
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // sentinel 记录
        Tracer.trace((Throwable) objects[0]);
        Object result = method.invoke(target, objects);
        return result;
    }
}
