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

package com.xhuicloud.common.mybatis.tenant;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
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
        Integer tenantId = XHuiCommonThreadLocalHolder.getTenant();
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
        Integer tenantId = XHuiCommonThreadLocalHolder.getTenant();
        if (tenantId == null || TENANT_CACHE == null) {
            return Boolean.TRUE;
        }
        return !TENANT_CACHE.contains(tableName);
    }
}
