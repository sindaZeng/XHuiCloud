package com.zsinda.fdp.aspect;

import com.zsinda.fdp.annotation.ChooseDataSource;
import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @program: FDPlatform
 * @description: DynamicDataSourceAnnotationAdvisor
 * @author: Sinda
 * @create: 2020-06-22 17:48
 */
public class DynamicDataSourceAnnotationAdvisor extends AbstractPointcutAdvisor implements
        BeanFactoryAware {

    private Advice advice;

    private Pointcut pointcut;

    public DynamicDataSourceAnnotationAdvisor(
            @NonNull DynamicDataSourceInterceptor dynamicDataSourceAnnotationInterceptor) {
        this.advice = dynamicDataSourceAnnotationInterceptor;
        this.pointcut = buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(ChooseDataSource.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(ChooseDataSource.class);
        return new ComposablePointcut(cpc).union(mpc);
    }
}
