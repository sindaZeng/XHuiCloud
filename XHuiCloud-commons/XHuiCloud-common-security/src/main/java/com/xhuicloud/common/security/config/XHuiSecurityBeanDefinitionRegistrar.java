package com.xhuicloud.common.security.config;

import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import com.xhuicloud.common.core.constant.AuthorizationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @program: XHuiCloud
 * @description: 资源服务器配置注入spring ioc {@link EnableXHuiResourceServer} 因为是注入 security 的资源服务器配置。有些地方不需要用到。
 * @author: Sinda
 * @create: 2019-12-28 21:16:50
 **/
@Slf4j
public class XHuiSecurityBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 根据注解值动态注入资源服务器的相关属性
     *
     * @param metadata 注解信息
     * @param registry 注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        if (registry.isBeanNameInUse(AuthorizationConstants.RESOURCE_SERVER_CONFIGURER)) {
            return;
        }
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(XHuiResourceServerConfigurerAdapter.class);
        registry.registerBeanDefinition(AuthorizationConstants.RESOURCE_SERVER_CONFIGURER, beanDefinition);
    }

}
