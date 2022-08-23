/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.authorization.resource.properties;

import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Data
@ConfigurationProperties(prefix = "xhuicloud.security")
public class SecurityProperties implements InitializingBean {

    /**
     * Standard data formats for OAuth 2.0 Tokens.
     */
    public String accessTokenFormat = OAuth2TokenFormat.REFERENCE;

    /**
     * 授权服务器配置
     */
    public Authorization authorization = new Authorization();

    /**
     * 授权服务器配置
     */
    public ResourceServer resourceServer = new ResourceServer();

    @Getter
    @Setter
    public static class ResourceServer {

        /**
         * 用于使用令牌内省端点进行身份验证的客户端id。
         */
        private String clientId;

        /**
         * 用于使用令牌内省端点进行身份验证的客户端密钥
         */
        private String clientSecret;

        /**
         * OAuth 2.0令牌自省端点。
         */
        private String introspectionUri;

        /**
         * 资源服务器请求白名单
         */
        private List<String> ignoreUrls = new ArrayList<>();

        private String issuerUri;

        private String jwkSetUri;

    }

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

        /**
         * 自定义jwk
         */
        private Jwk jwk = new Jwk();

        /**
         * 自定义确认授权页面
         */
        private String consentPage = "/authorize/confirm_access";

        /**
         * 授权失败页面
         */
        private String errorPage = "/authorize/error";

        /**
         * the URL the Provider uses as its Issuer Identifier.
         */
        private String issuer = "http://localhost:16000";

        /**
         * 认证授权服务器请求白名单
         */
        private List<String> ignoreUrls = new ArrayList<>();

    }

    @Getter
    @Setter
    public static class Jwk {

        private String keyStore;

        private String keyPath;

        private String keyPassword;

        private String storePass;

        private String alias;

    }

    /**
     * 令牌模式
     */
    public interface OAuth2TokenFormat {

        /**
         * JWT
         */
        String SELF_CONTAINED = "self-contained";

        /**
         * OpaqueToken
         */
        String REFERENCE = "reference";

    }

    /**
     * 白名单端点
     */
    @Override
    public void afterPropertiesSet() {
        final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
        RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        for (RequestMappingInfo info : map.keySet()) {
            HandlerMethod handlerMethod = map.get(info);
            // 1. 获取类上的注解
            Anonymous controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Anonymous.class);
            Optional.ofNullable(controller).ifPresent(anonymous -> info.getPatternsCondition().getPatterns()
                    .forEach(url -> resourceServer.getIgnoreUrls().add(ReUtil.replaceAll(url, PATTERN, "*"))));
            // 2. 获取方法上的注解
            Anonymous method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
            Optional.ofNullable(method).ifPresent(anonymous -> info.getPatternsCondition().getPatterns()
                    .forEach(url -> resourceServer.getIgnoreUrls().add(ReUtil.replaceAll(url, PATTERN, "*"))));
        }
    }

}
