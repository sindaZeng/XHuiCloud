package com.xhuicloud.common.authorization.resource.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xhuicloud.security")
public class SecurityProperties {

    /**
     * 授权服务器配置
     */
    private Authorization authorization = new Authorization();



    @Getter
    @Setter
    public static class Authorization {

        /**
         * 默认请求令牌有效期
         */
        private int accessTokenValiditySeconds = 60 * 60 * 12;

        /**
         * 默认刷新令牌有效期
         */
        private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;

    }

}