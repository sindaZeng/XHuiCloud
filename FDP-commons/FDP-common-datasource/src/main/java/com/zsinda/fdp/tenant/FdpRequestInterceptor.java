package com.zsinda.fdp.tenant;

import com.zsinda.fdp.constant.CommonConstants;
import com.zsinda.fdp.context.TenantHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @program: FDPlatform
 * @description: feign 请求头传递
 * @author: Sinda
 * @create: 2020-05-12 18:31
 */
public class FdpRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        if (TenantHolder.getTenant() != null) {
            template.header(CommonConstants.TENANT_ID, TenantHolder.getTenant().toString());
        }
    }
}
