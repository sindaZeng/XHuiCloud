package com.zsinda.fdp.handler;

import cn.hutool.http.HttpStatus;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zsinda.fdp.utils.R;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 降级 限流策略
 */
@Slf4j
public class FDPUrlBlockHandler implements UrlBlockHandler {
	@Override
	public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
		log.error("sentinel 降级 资源名称{}", e.getRule().getResource(), e);
		response.setContentType(ContentType.JSON.toString());
		response.setStatus(HttpStatus.HTTP_UNAVAILABLE);
		response.getWriter().print(
				JSONUtil.toJsonStr(R.failed(e.getMessage())));
	}
}
