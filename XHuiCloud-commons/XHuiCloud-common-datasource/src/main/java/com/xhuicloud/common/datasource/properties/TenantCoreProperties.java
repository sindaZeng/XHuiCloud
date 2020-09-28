package com.xhuicloud.common.datasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: 租户核心配置
 * @author: Sinda
 * @create: 2020-05-13 11:52
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "xhuicloud.tenant")
public class TenantCoreProperties {

    /**
     * 租户字段
     */
    private String column = "tenant_id";

    /**
     * 需要校验租户的表
     */
    private List<String> table = Arrays.asList(
            "sys_dept",
            "sys_dict",
            "sys_file",
            "sys_log",
            "sys_role",
            "sys_user");
}
