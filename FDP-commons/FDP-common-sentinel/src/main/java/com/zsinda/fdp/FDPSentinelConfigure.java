package com.zsinda.fdp;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.zsinda.fdp.handler.FDPUrlBlockHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * sentinel 配置
 */
public class FDPSentinelConfigure implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		addSpringMvcInterceptor(registry);
	}

	private void addSpringMvcInterceptor(InterceptorRegistry registry) {
		SentinelWebMvcConfig config = new SentinelWebMvcConfig();
		config.setBlockExceptionHandler(new FDPUrlBlockHandler());
		config.setHttpMethodSpecify(true);
		config.setOriginParser(request -> request.getHeader("S-user"));
		registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
	}
}
