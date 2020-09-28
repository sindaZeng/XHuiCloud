package com.xhuicloud.common.datasource.transaction;

import com.xhuicloud.common.datasource.properties.DataSourceCoreProperties;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

/**
 * @program: XHuiCloud
 * @description: 多数据源动态事务工厂
 * @author: Sinda
 * @create: 2019-09-09
 **/
@AllArgsConstructor
public class DynamicTransactionFactory extends SpringManagedTransactionFactory {

    private DataSourceCoreProperties dataSourceCoreProperties;

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new DynamicTransaction(dataSource, dataSourceCoreProperties.getDatasources());
    }
}
