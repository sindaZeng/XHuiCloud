package com.xhuicloud.common.datasource.aspect;

import com.xhuicloud.common.datasource.annotation.ChooseDataSource;
import com.xhuicloud.common.datasource.context.DynamicDataSourceHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @program: XHuiCloud
 * @description: DynamicDataSourceInterceptor
 * @author: Sinda
 * @create: 2020-06-22 17:41
 */
public class DynamicDataSourceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            String dataBaseName = getDataBaseName(methodInvocation);
            DynamicDataSourceHolder.setDataSource(Objects.nonNull(dataBaseName) ? dataBaseName : "");
            return methodInvocation.proceed();
        }finally {
            DynamicDataSourceHolder.removeDataSource();
        }
    }

    private String getDataBaseName(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        ChooseDataSource cd = method.isAnnotationPresent(ChooseDataSource.class)
                ? method.getAnnotation(ChooseDataSource.class)
                : AnnotationUtils.findAnnotation(invocation.getMethod().getDeclaringClass(), ChooseDataSource.class);
        String key = cd.value();
        return (!key.isEmpty()) ? key : null;
    }
}
