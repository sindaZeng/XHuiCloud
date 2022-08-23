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

package com.xhuicloud.common.authorization.resource.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.constant.AuthorizationConstants;
import com.xhuicloud.common.core.utils.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @Desc Token 转发
 * @Author Sinda
 * @Date 2022/8/23
 */
public class OAuth2TokenRequestInterceptor implements RequestInterceptor {

    private final BearerTokenResolver tokenResolver = new DefaultBearerTokenResolver();

    @Override
    public void apply(RequestTemplate requestTemplate) {

        Collection<String> isFrom = requestTemplate.headers().get(AuthorizationConstants.FROM);
        if (CollUtil.isNotEmpty(isFrom) && isFrom.contains(AuthorizationConstants.IS_COMMING_ANONYMOUS_YES)) {
            return;
        }

        if (WebUtils.getRequest() == null) {
            return;
        }
        HttpServletRequest request = WebUtils.getRequest();

        String token = tokenResolver.resolve(request);
        if (StrUtil.isNotBlank(token)) {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", OAuth2AccessToken.TokenType.BEARER.getValue(), token));
        }
    }
}
