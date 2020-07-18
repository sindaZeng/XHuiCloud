package com.zsinda.fdp.interceptor;

import cn.hutool.core.util.StrUtil;
import com.zsinda.fdp.constant.CommonConstants;
import com.zsinda.fdp.utils.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: FDPlatform
 * @description:
 * @author: Sinda
 * @create: 2020/7/18 9:36 下午
 */
@Slf4j
public class GlobalGrayFeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String version = WebUtils.getRequest() != null ? WebUtils.getRequest().getHeader(CommonConstants.VERSION)
                : null;

        if (StrUtil.isNotBlank(version)) {
            log.debug("当前请求版本 :{}", version);
            template.header(CommonConstants.VERSION, version);
        }
    }
}
