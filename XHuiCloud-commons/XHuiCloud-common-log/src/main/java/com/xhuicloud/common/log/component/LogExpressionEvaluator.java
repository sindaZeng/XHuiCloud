package com.xhuicloud.common.log.component;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/1/30
 */
public class LogExpressionEvaluator extends CachedExpressionEvaluator {

    private Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);

    public String parseExpressionToStr(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(this.expressionCache, methodKey, conditionExpression).getValue(evalContext, String.class);
    }

    public <T> T parseExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext, Class<T> desiredResultType) {
        return getExpression(this.expressionCache, methodKey, conditionExpression).getValue(evalContext, desiredResultType);
    }

}
