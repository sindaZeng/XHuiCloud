package com.xhuicloud.common.log.component;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/1/30
 */
public class LogExpressionParser {

    private final LogExpressionEvaluator evaluator;

    public EvaluationContext createEvaluationContext(Object rootObject, Method method, Object[] arguments, Object ret, String errMsg) {
        return new LogEvaluationContext(rootObject, method, arguments, new DefaultParameterNameDiscoverer(), ret, errMsg);
    }

    public String getExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return evaluator.parseExpressionToStr(conditionExpression, methodKey, evalContext);
    }

    public <T> T getExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext, Class<T> desiredResultType) {
        return evaluator.parseExpression(conditionExpression, methodKey, evalContext, desiredResultType);
    }

    public LogExpressionParser() {
        this.evaluator = new LogExpressionEvaluator();
    }
}
