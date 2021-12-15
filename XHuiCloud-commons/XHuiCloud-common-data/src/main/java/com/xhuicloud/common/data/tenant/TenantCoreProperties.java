package com.xhuicloud.common.data.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
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

}
