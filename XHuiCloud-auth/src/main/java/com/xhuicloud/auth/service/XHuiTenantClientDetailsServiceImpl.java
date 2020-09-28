package com.xhuicloud.auth.service;

import com.xhuicloud.common.core.constant.AuthorizationConstants;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.datasource.tenant.XHuiTenantHolder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/25 5:04 下午
 */
@Service
public class XHuiTenantClientDetailsServiceImpl extends JdbcClientDetailsService {

    public XHuiTenantClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Cacheable(value = CacheConstants.CLIENT_DETAILS, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        super.setSelectClientDetailsSql(
                String.format(AuthorizationConstants.DEFAULT_SELECT_STATEMENT, XHuiTenantHolder.getTenant()));
        return super.loadClientByClientId(clientId);
    }
}
