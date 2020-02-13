package com.zsinda.fdp.handler;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zsinda.fdp.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 降级 限流策略
 */
@Slf4j
@Component
public class FDPUrlBlockHandler implements BlockExceptionHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
		log.error("sentinel 降级 资源名称{}", e.getRule().getResource(), e);
		response.setContentType(ContentType.JSON.toString());
		response.setStatus(HttpStatus.HTTP_UNAVAILABLE);
		response.getWriter().print(
				JSONUtil.toJsonStr(R.failed(e.getMessage())));
	}
}
