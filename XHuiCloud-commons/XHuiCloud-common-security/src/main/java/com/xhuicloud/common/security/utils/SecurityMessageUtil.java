package com.xhuicloud.common.security.utils;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/27 5:21 下午
 */
public class SecurityMessageUtil extends ReloadableResourceBundleMessageSource {

    public SecurityMessageUtil() {
        setBasename("classpath:security/messages");
        setDefaultLocale(Locale.CHINA);
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new SecurityMessageUtil());
    }

}
