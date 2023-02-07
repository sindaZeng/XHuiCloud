package com.xhuicloud.common.log.component;

import com.xhuicloud.common.log.utils.LogRecordContext;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/1/30
 */
public class LogEvaluationContext extends MethodBasedEvaluationContext {

    public LogEvaluationContext(Object rootObject, Method method, Object[] arguments,
                                ParameterNameDiscoverer parameterNameDiscoverer, Object ret, String errorMsg) {
        //把方法的参数都放到 SpEL 解析的 RootObject 中
        super(rootObject, method, arguments, parameterNameDiscoverer);
        //把 LogRecordContext 中的变量都放到 RootObject 中
        Map<String, Object> variables = LogRecordContext.getVariables();
        if (variables != null && variables.size() > 0) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                setVariable(entry.getKey(), entry.getValue());
            }
        }
        //把方法的返回值和 ErrorMsg 都放到 RootObject 中
        setVariable("_ret", ret);
        setVariable("_errorMsg", errorMsg);
    }
}
