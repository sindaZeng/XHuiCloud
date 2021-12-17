package com.xhuicloud.push.session;

import cn.hutool.extra.spring.SpringUtil;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.push.enums.PushTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DefaultPushSessionFactory implements PushSessionFactory, ApplicationContextAware {

    private PushTypeEnum pushTypeEnum;

    private static ApplicationContext applicationContext;

    @Override
    public PushSession getPushSession(PushTypeEnum pushTypeEnum) {
        if (pushTypeEnum == null) {
            throw SysException.sysFail("pushTypeEnum is not null");
        }
        this.pushTypeEnum = pushTypeEnum;
        return applicationContext.getBean(pushTypeEnum.name(), PushSession.class);
    }

    @Override
    public PushSession getPushSession(String type) {
        return getPushSession(PushTypeEnum.valueOf(type));
    }

    @Override
    public PushTypeEnum getPushTypeEnum() {
        return this.pushTypeEnum;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DefaultPushSessionFactory.applicationContext = applicationContext;
    }
}
