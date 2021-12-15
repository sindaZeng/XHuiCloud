package com.xhuicloud.common.datasource.tenant;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.xhuicloud.common.data.tenant.TenantCoreProperties;
import com.xhuicloud.common.data.tenant.XHuiTenantHolder;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.jsqlparser.expression.Expression;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: XHuiTenantHandler
 * @author: Sinda
 * @create: 2020-05-12 16:56
 */
public class XHuiTenantHandler implements TenantLineHandler {

    @Autowired
    private TenantCoreProperties tenantCoreProperties;

    private static List<String> TENANT_CACHE = null;

    @PostConstruct
    private void init() {
        List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
        if (CollectionUtil.isNotEmpty(tableInfos)) {
            TENANT_CACHE = new ArrayList<>();
            tableInfos.stream().forEach(tableInfo -> {
                if (tableInfo.getFieldList().stream().anyMatch(fieldInfo -> fieldInfo.getColumn().equals(tenantCoreProperties.getColumn()))) {
                    TENANT_CACHE.add(tableInfo.getTableName());
                }
            });
        }
    }

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
        if (null == TENANT_CACHE || TENANT_CACHE.size() == 0) {
            init();
        }
        return !TENANT_CACHE.contains(tableName);
    }
}
