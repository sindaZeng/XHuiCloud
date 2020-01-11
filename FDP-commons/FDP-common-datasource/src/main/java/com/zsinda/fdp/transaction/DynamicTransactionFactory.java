package com.zsinda.fdp.transaction;

import com.zsinda.fdp.properties.DataSourceCoreProperties;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

@AllArgsConstructor
public class DynamicTransactionFactory extends SpringManagedTransactionFactory {

    private DataSourceCoreProperties dataSourceCoreProperties;

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new DynamicTransaction(dataSource, dataSourceCoreProperties.getDatasources());
    }
}
