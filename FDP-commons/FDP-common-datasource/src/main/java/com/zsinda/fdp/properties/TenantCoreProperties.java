package com.zsinda.fdp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @program: FDPlatform
 * @description: 租户核心配置
 * @author: Sinda
 * @create: 2020-05-13 11:52
 */
@Data
@Component
@ConfigurationProperties(prefix = "fdp.tenant")
public class TenantCoreProperties {

    /**
     * 租户字段
     */
    private String column = "tenant_id";

    /**
     * 需要校验租户的表
     */
    private List<String> talbe = Arrays.asList(
            "sys_dept",
            "sys_dict",
            "sys_file",
            "sys_log",
            "sys_role",
            "sys_user");
}
