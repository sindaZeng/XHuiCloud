package com.zsinda.fdp.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.zsinda.fdp.constant.CommonConstants;
import com.zsinda.fdp.context.TenantHolder;
import com.zsinda.fdp.properties.TenantCoreProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: FDPlatform
 * @description: FdpTenantHandler
 * @author: Sinda
 * @create: 2020-05-12 16:56
 */
@Component
public class FdpTenantHandler implements TenantHandler {

    @Autowired
    private TenantCoreProperties tenantCoreProperties;

    /**
     * 设置租户id
     *
     * @param where
     * @return
     */
    @Override
    public Expression getTenantId(boolean where) {
        Integer tenantId = TenantHolder.getTenant();
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
    public boolean doTableFilter(String tableName) {
        return TenantHolder.getTenant() == null
                || TenantHolder.getTenant() == Integer.valueOf(CommonConstants.DEFAULT_TENANT_ID)
                || !tenantCoreProperties.getTalbe().contains(tableName);
    }
}
