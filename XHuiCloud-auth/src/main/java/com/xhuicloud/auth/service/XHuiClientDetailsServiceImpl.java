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

package com.xhuicloud.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.upms.entity.SysClientDetails;
import com.xhuicloud.upms.feign.SysClientDetailFeign;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Map;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/25 5:04 下午
 */
@Slf4j
@Service
@AllArgsConstructor
public class XHuiClientDetailsServiceImpl implements ClientDetailsService {

    private final SysClientDetailFeign sysClientDetailFeign;

    @Override
//    @Cacheable(value = CacheConstants.CLIENT_DETAILS, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        SysClientDetails data = sysClientDetailFeign.getById(clientId, IS_COMMING_INNER_YES).getData();

        if (data == null) {
            return null;
        }
        // 适配成oauth2内置类型
        return clientDetailsWrapper(data);
    }

    private ClientDetails clientDetailsWrapper(SysClientDetails origin) {
        BaseClientDetails target = new BaseClientDetails();
        // 必选项
        target.setClientId(origin.getClientId());
        target.setClientSecret(String.format("{noop}%s", origin.getClientSecret()));

        if (ArrayUtil.isNotEmpty(origin.getAuthorizedGrantTypes())) {
            target.setAuthorizedGrantTypes(CollUtil.newArrayList(origin.getAuthorizedGrantTypes()));
        }

        if (StrUtil.isNotBlank(origin.getAuthorities())) {
            target.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(origin.getAuthorities()));
        }

        if (StrUtil.isNotBlank(origin.getResourceIds())) {
            target.setResourceIds(StringUtils.commaDelimitedListToSet(origin.getResourceIds()));
        }

        if (StrUtil.isNotBlank(origin.getWebServerRedirectUri())) {
            target.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(origin.getWebServerRedirectUri()));
        }

        if (StrUtil.isNotBlank(origin.getScope())) {
            target.setScope(StringUtils.commaDelimitedListToSet(origin.getScope()));
        }

        if (StrUtil.isNotBlank(origin.getAutoapprove())) {
            target.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(origin.getAutoapprove()));
        }

        if (origin.getAccessTokenValidity() != null) {
            target.setAccessTokenValiditySeconds(origin.getAccessTokenValidity());
        }

        if (origin.getRefreshTokenValidity() != null) {
            target.setRefreshTokenValiditySeconds(origin.getRefreshTokenValidity());
        }

        String json = origin.getAdditionalInformation();
        if (StrUtil.isNotBlank(json)) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> additionalInformation = JSONUtil.toBean(json, Map.class);
                target.setAdditionalInformation(additionalInformation);
            }
            catch (Exception e) {
                log.warn("Could not decode JSON for additional information: " + json, e);
            }
        }

        return target;
    }
}
