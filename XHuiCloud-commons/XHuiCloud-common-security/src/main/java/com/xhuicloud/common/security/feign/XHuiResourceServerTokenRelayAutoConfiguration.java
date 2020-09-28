package com.xhuicloud.common.security.feign;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.cloud.security.oauth2.client.ResourceServerTokenRelayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.annotation.*;

/**
 * @program: XHuiCloud
 * @description: 注入AccessTokenContextRelay 解决feign 传递token 为空问题
 * @author: Sinda
 * @create: 2019-12-28 00:12
 */
@Configuration
@AutoConfigureAfter(OAuth2AutoConfiguration.class)
@ConditionalOnWebApplication
@ConditionalOnProperty("security.oauth2.client.client-id")
public class XHuiResourceServerTokenRelayAutoConfiguration {

	/**
	 * 上下文token 中转器
	 * 在这个方法{@link ResourceServerTokenRelayAutoConfiguration.ResourceServerTokenRelayRegistrationAutoConfiguration#addInterceptors}
	 * 中，是可以看到oauth2 资源服务器是直接new了个拦截器转发出去{@link HandlerInterceptorAdapter}
	 *
	 * 1、当请求上线文没有Token,如果调用feign 会直接，这个OAuth2FeignRequestInterceptor 肯定会报错，因为上下文copy 失败
	 * 2、如果设置线程隔离，这里也会报错。导致安全上下问题传递不到子线程中。
	 * 3、强制使用拦截器去处理 token 转发到这里上下文，使用的业务场景只有这里，影响性能高
	 *  https://www.codercto.com/a/68987.html
	 * @param context
	 * @return
	 */
	@Bean
	public AccessTokenContextRelay accessTokenContextRelay(OAuth2ClientContext context) {
		return new AccessTokenContextRelay(context);
	}

	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Conditional(OAuth2OnClientInResourceServerCondition.class)
	@interface ConditionalOnOAuth2ClientInResourceServer {

	}

	private static class OAuth2OnClientInResourceServerCondition
		extends AllNestedConditions {

		public OAuth2OnClientInResourceServerCondition() {
			super(ConfigurationPhase.REGISTER_BEAN);
		}

		@ConditionalOnBean(ResourceServerConfiguration.class)
		static class Server {
		}

		@ConditionalOnBean(OAuth2ClientConfiguration.class)
		static class Client {
		}

	}
}
