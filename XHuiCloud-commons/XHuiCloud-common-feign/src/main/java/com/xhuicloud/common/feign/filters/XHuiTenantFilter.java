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

package com.xhuicloud.common.feign.filters;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: XHuiCloud
 * @description: 租户过滤器
 * @author: Sinda
 * @create: 2020-05-12 17:45
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XHuiTenantFilter extends GenericFilterBean {

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String tenantId = request.getHeader(CommonConstants.TENANT_ID);
        if (StrUtil.isNotBlank(tenantId)) {
            XHuiCommonThreadLocalHolder.setTenant(Integer.parseInt(tenantId));
        } else {
            tenantId = request.getParameter(CommonConstants.TENANT_ID);
            if (StrUtil.isNotBlank(tenantId)) {
                XHuiCommonThreadLocalHolder.setTenant(Integer.parseInt(tenantId));
            } else {
                XHuiCommonThreadLocalHolder.setTenant(CommonConstants.DEFAULT_TENANT_ID);
            }
        }
        log.debug("租户id为:[{}]", tenantId);
        filterChain.doFilter(request, response);
        XHuiCommonThreadLocalHolder.removeTenant();
    }

}
