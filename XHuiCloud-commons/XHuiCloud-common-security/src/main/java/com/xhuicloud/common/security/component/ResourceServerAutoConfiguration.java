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

package com.xhuicloud.common.security.component;

import com.xhuicloud.common.security.handle.XHuiResponseErrorHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: XHuiCloud
 * @author: Sinda
 * @create: 2020-03-04 14:21:15
 **/
@ComponentScan("com.xhuicloud.common.security")
public class ResourceServerAutoConfiguration implements ApplicationContextAware, BeanPostProcessor {

	private ApplicationContext applicationContext;

	@Bean
	@Primary
	@LoadBalanced
	public RestTemplate lbRestTemplate() {
		// 获取上下文配置的ClientHttpRequestInterceptor 实现
		Map<String, ClientHttpRequestInterceptor> beansOfType = applicationContext
				.getBeansOfType(ClientHttpRequestInterceptor.class);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(new ArrayList<>(beansOfType.values()));
		restTemplate.setErrorHandler(new XHuiResponseErrorHandler());
		return restTemplate;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 添加自定义Voter
	 * AffirmativeBased 1票即可通过
	 * ConsensusBased 判断同意或拒绝票数 谁多
	 * UnanimousBased 1票拒绝就不通过
	 *
	 *  资源url权限校验
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof AffirmativeBased) {
			AffirmativeBased affirmativeBased = (AffirmativeBased) bean;
			List<AccessDecisionVoter<?>> decisionVoters = affirmativeBased.getDecisionVoters();
			// 硬编码 必须为首位
			decisionVoters.add(0, new XHuiResourceUrlVoter());
			return new AffirmativeBased(decisionVoters);
		}
		return bean;
	}
}
