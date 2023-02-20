package com.xhuicloud.common.log.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.xhuicloud.common.core.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.common.core.utils.DateUtils;
import com.xhuicloud.common.core.utils.WebUtils;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.common.log.component.IOperatorGetService;
import com.xhuicloud.common.log.component.LogExpressionParser;
import com.xhuicloud.common.log.component.MethodResult;
import com.xhuicloud.common.log.constant.LogConstant;
import com.xhuicloud.common.log.model.AuditModel;
import com.xhuicloud.common.log.model.Operator;
import com.xhuicloud.common.log.utils.LogRecordContext;
import com.xhuicloud.common.mq.entity.push.PushMqEntity;
import com.xhuicloud.common.mq.service.CommonMqService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/1/30
 */
@Slf4j
@Aspect
public class AuditRecordAspect {

    private final LogExpressionParser parser;

    private final CommonMqService commonMqService;

    private final IOperatorGetService iOperatorGetService;

    private Class<?> cls;

    public AuditRecordAspect(CommonMqService commonMqService, IOperatorGetService iOperatorGetService) {
        this.parser = new LogExpressionParser();
        this.commonMqService = commonMqService;
        this.iOperatorGetService = iOperatorGetService;
        this.cls = null;

        try {
            this.cls = Class.forName("com.xhuicloud.common.authorization.resource.utils.SecurityHolder");
        } catch (ClassNotFoundException e) {
        }
    }

    @Value("${spring.application.name}")
    private String application;

    @SneakyThrows
    @Around("@annotation(auditRecord)")
    public Object around(ProceedingJoinPoint point, AuditRecord auditRecord) {
        MethodResult methodResult = new MethodResult(true, null, "");
        Object result = null;
        // 创建当前方法日志上下文
        LogRecordContext.putEmptySpan();
        Method method = getMethod(point);
        AuditRecord annotation = getAnnotation(method);
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            methodResult = new MethodResult(false, throwable, throwable.getLocalizedMessage());
        }
        try {
            recordExecute(result, method, annotation, point,
                    methodResult.getErrorMsg());
        } catch (Throwable throwable) {
            log.error("日志记录异常", throwable);
        } finally {
            LogRecordContext.clean();
        }
        if (methodResult.getThrowable() != null) {
            throw methodResult.getThrowable();
        }
        return result;
    }

    private void recordExecute(Object result, Method method, AuditRecord annotation, ProceedingJoinPoint point, String errorMsg) {
        String condition = annotation.condition();
        String[] anonymousFields = annotation.anonymousFields();
        String value = annotation.value();
        Class<?> targetClass = getTargetClass(point);
        // 参数
        Object[] args = point.getArgs();
        // 创建上下文
        EvaluationContext evaluationContext = parser.createEvaluationContext(point.getThis(), method, args, result, errorMsg);
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        if (StrUtil.isBlank(condition) || parser.getExpression(condition, annotatedElementKey, evaluationContext, Boolean.class)) {
            try {
                value = parser.getExpression(value, annotatedElementKey, evaluationContext);
            } catch (IllegalStateException i) {
                log.error("无表达式");
            }
            HttpServletRequest request = WebUtils.getRequest();
            AuditModel auditModel = new AuditModel();
            auditModel.setReqId(XHuiCommonThreadLocalHolder.getReqId());
            auditModel.setRequestIp(WebUtils.getIP());
            auditModel.setServiceSystem(application);
            auditModel.setDetail(value);
            auditModel.setOperator(getOperator());
            auditModel.setClassPath(targetClass.getName());
            auditModel.setRequestMethod(point.getSignature().getName());
            auditModel.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            auditModel.setHttpMethod(request.getMethod());
            List<Object> params = new ArrayList<>();
            JSONObject resultObject = JSONUtil.parseObj(result);
            if (anonymousFields.length > 0) {
                for (Object arg : args) {
                    JSONObject jsonObject = JSONUtil.parseObj(arg);
                    toAnonymousFields(jsonObject, anonymousFields);
                    params.add(jsonObject);
                }
                toAnonymousFields(resultObject, anonymousFields);
            }
            auditModel.setParams(JSONUtil.toJsonStr(params));
            auditModel.setResult(resultObject.toString());
            auditModel.setStatus(StrUtil.isBlank(errorMsg) ? 1 : 0);
            auditModel.setErrorMsg(errorMsg);
            auditModel.setCreateTime(DateUtils.getNowDate());
            PushMqEntity pushMqEntity = new PushMqEntity();
            pushMqEntity.setCls(AuditModel.class);
            pushMqEntity.setJson(JSON.toJSONString(auditModel));
            commonMqService.persistent(true).sendDirect(LogConstant.AUDIT_RECORD_QUEUE_NAME, pushMqEntity);
        }
    }

    private void toAnonymousFields(JSONObject resultObject, String[] anonymousFields) {
        for (String anonymousField : anonymousFields) {
            Object field = resultObject.get(anonymousField);
            if (ObjectUtil.isNotEmpty(field)) {
                resultObject.set(anonymousField, "******");
            }
        }
    }

    private Method getMethod(ProceedingJoinPoint point) {
        return ((MethodSignature) point.getSignature()).getMethod();
    }

    private Class<?> getTargetClass(ProceedingJoinPoint point) {
        return point.getTarget().getClass();
    }

    private AuditRecord getAnnotation(Method method) {
        AuditRecord annotation = method.getAnnotation(AuditRecord.class);
        return annotation;
    }

    /**
     * @return
     */
    private String getOperator() {
        if (iOperatorGetService != null) {
            Operator user = iOperatorGetService.getUser();
            return user.getOperatorName() + "(" + user.getOperatorId() + ")";
        } else if (cls != null) {
            try {
                MethodType methodType = MethodType.methodType(String.class);
                MethodHandle method = MethodHandles.lookup()
                        .findStatic(cls, "getOperator", methodType);
                return method.invoke().toString();
            } catch (Throwable e) {
            }
        }
        return "匿名用户";
    }
}
