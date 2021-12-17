package com.xhuicloud.common.feign.interceptor;

import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.data.tenant.XHuiCommonThreadLocalHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/27 11:08 上午
 */
@Slf4j
public class XHuiFeignTenantInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (XHuiCommonThreadLocalHolder.getTenant() == null) {
            return;
        }
        requestTemplate.header(CommonConstants.TENANT_ID, XHuiCommonThreadLocalHolder.getTenant().toString());
    }

}

