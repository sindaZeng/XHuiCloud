package com.zsinda.fdp.tenant;

import cn.hutool.core.util.StrUtil;
import com.zsinda.fdp.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @program: FDPlatform
 * @description: 租户过滤器
 * @author: Sinda
 * @create: 2020-05-12 17:45
 */
@Slf4j
@Component
public class FdpTenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tenantId = httpServletRequest.getHeader(CommonConstants.TENANT_ID);
        if (StrUtil.isNotBlank(tenantId)) {
            FdpTenantHolder.setTenant(Integer.parseInt(tenantId));
        } else {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            tenantId = httpServletRequest.getParameter(CommonConstants.TENANT_ID);
            if (StrUtil.isNotBlank(tenantId)) {
                FdpTenantHolder.setTenant(Integer.parseInt(tenantId));
            } else {
                FdpTenantHolder.setTenant(CommonConstants.DEFAULT_TENANT_ID);
            }
        }
        log.debug("租户id为:[{}]", tenantId);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        FdpTenantHolder.removeTenant();
    }
}
