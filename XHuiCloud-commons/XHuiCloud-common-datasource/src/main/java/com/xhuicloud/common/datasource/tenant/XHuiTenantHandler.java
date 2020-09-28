package com.xhuicloud.common.datasource.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.xhuicloud.common.datasource.properties.TenantCoreProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: XHuiCloud
 * @description: FdpTenantHandler
 * @author: Sinda
 * @create: 2020-05-12 16:56
 */
public class XHuiTenantHandler implements TenantLineHandler {

    @Autowired
    private TenantCoreProperties tenantCoreProperties;

    /**
     * 设置租户id
     *
     * @return
     */
    @Override
    public Expression getTenantId() {
        Integer tenantId = XHuiTenantHolder.getTenant();
        if (tenantId == null) {
            return new NullValue();
        }
        return new LongValue(tenantId);
    }

    /**
     * 数据库对应字段
     *
     * @return
     */
    @Override
    public String getTenantIdColumn() {
        return tenantCoreProperties.getColumn();
    }

    /**
     * 需要过滤的条件
     *
     * @param tableName
     * @return
     */
    @Override
    public boolean ignoreTable(String tableName) {
        Integer tenantId = XHuiTenantHolder.getTenant();
        if (tenantId == null) {
            return Boolean.TRUE;
        }
        return !tenantCoreProperties.getTable().contains(tableName);
    }
}
