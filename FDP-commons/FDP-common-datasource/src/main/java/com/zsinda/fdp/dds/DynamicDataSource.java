package com.zsinda.fdp.dds;

import cn.hutool.core.util.ObjectUtil;
import com.zsinda.fdp.context.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: FDPlatform
 * @description: 动态数据源核心
 * @author: Sinda
 * @create: 2019-09-03
 **/
@Slf4j
public class DynamicDataSource extends AbstractDataSource {

    private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    /**
     * 添加数据源
     *
     * @param k 数据源名称
     * @param v 数据源
     */
    public void putDataSourceMap(String k, DataSource v) {
        dataSourceMap.put(k, v);
    }

    /**
     * 选择数据源
     *
     * @return
     */
    protected DataSource determineTargetDataSource() {
        Assert.notNull(dataSourceMap, "DataSource router not initialized");
        Object lookupKey = determineCurrentLookupKey();
        DataSource dataSource;
        if (ObjectUtil.isEmpty(lookupKey)) {
            // 如果当前线程没有选择数据源，默认第一个
            dataSource = dataSourceMap.get(dataSourceMap.keySet().iterator().next());
        } else {
            dataSource = dataSourceMap.get(lookupKey);
        }
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    /**
     * 数据源路由，选取的数据源逻辑名称
     */
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }
}
