package com.xhuicloud.common.data.tenant;

import com.xhuicloud.common.core.constant.CommonConstants;
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
        if (XHuiTenantHolder.getTenant() == null) {
            return;
        }
        requestTemplate.header(CommonConstants.TENANT_ID, XHuiTenantHolder.getTenant().toString());
    }

}

