package com.xhuicloud.common.log.annotation;

import com.xhuicloud.common.log.XHuiLogAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/20
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(XHuiLogAutoConfiguration.class)
public @interface EnableAudit {
}
