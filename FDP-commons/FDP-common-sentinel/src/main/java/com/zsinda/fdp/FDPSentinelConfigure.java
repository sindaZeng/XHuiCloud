package com.zsinda.fdp;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.zsinda.fdp.handler.FDPUrlBlockHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * sentinel 配置
 */
@Configuration
public class FDPSentinelConfigure {

	@PostConstruct
	public void initWebCallbackManager() {
		WebCallbackManager.setUrlBlockHandler(new FDPUrlBlockHandler());
	}

}
