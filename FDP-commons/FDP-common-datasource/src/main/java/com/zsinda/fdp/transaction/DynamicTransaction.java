package com.zsinda.fdp.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import com.zsinda.fdp.context.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 多数据源动态事务管理
 * @author x
 * @date 2019年09月09日
 */
@Slf4j
public class DynamicTransaction implements Transaction {

    private final DataSource dataSource;
    private final Map<String, DruidDataSource> dataSourceProperties;
    private Connection mainConnection;
    private String mainDatabaseldentification;
    private Map<String, Connection> otherConnectionMap = new ConcurrentHashMap<>();
    private boolean isConnectionTransactional;
    private boolean autoCommit;

    public DynamicTransaction(DataSource dataSource,Map<String, DruidDataSource> dataSourceProperties) {
        this.dataSource = dataSource;
        this.dataSourceProperties=dataSourceProperties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        String databaseldentification = DynamicDataSourceHolder.getDataSource();
        //如果 databaseldentification为空 或者它的值不属于当前拥有的数据源，就默认使用第一个数据源
        if (StringUtils.isEmpty(databaseldentification)||!dataSourceProperties.containsKey(databaseldentification)) {
            databaseldentification = dataSourceProperties.keySet().iterator().next();
        }
        if (databaseldentification.equals(mainDatabaseldentification)) {
            if (mainConnection != null) {
                return mainConnection;
            } else {
                openMainConnection();
                mainDatabaseldentification = databaseldentification;
                return mainConnection;
            }
        } else {
            //获取其他的数据源链接
            if (!otherConnectionMap.containsKey(databaseldentification)) {
                try {
                    Connection conn = dataSource.getConnection();
                    conn.setAutoCommit(this.autoCommit);
                    otherConnectionMap.put(databaseldentification, conn);
                } catch (SQLException ex) {
                    throw new CannotGetJdbcConnectionException("Could bot get JDBC otherConnection", ex);
                }
            }
            return otherConnectionMap.get(databaseldentification);
        }
    }

    private void openMainConnection() throws SQLException {
        this.mainConnection = DataSourceUtils.getConnection(this.dataSource);
        this.autoCommit = this.mainConnection.getAutoCommit();
        this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.mainConnection, this.dataSource);
        log.debug("JDBC mainConnection [{}] will ({}) be managed by spring", this.mainConnection, this.isConnectionTransactional ? "" : "not");
    }

    @Override
    public void commit() throws SQLException {
        if (this.mainConnection != null && this.isConnectionTransactional && !this.autoCommit) {
            log.debug("Committing JDBC mainConnection [{}]", this.mainConnection);
            this.mainConnection.commit();
        }
        for (Connection connection : otherConnectionMap.values()) {
            log.debug("Committing JDBC otherConnection [{}]", connection);
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (this.mainConnection != null && this.isConnectionTransactional && !this.autoCommit) {
            log.debug("Rolling back JDBC mainConnection [{}]", this.mainConnection);
            this.mainConnection.rollback();
        }
        for (Connection connection : otherConnectionMap.values()) {
            log.debug("Rolling back JDBC otherConnection [{}]", connection);
            connection.rollback();
        }
    }

    @Override
    public void close() {
        DataSourceUtils.releaseConnection(this.mainConnection, this.dataSource);
        for (Connection connection : otherConnectionMap.values()) {
            DataSourceUtils.releaseConnection(connection, this.dataSource);
        }
    }

    @Override
    public Integer getTimeout() {
        ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
        if (holder != null && holder.hasTimeout()) {
            return holder.getTimeToLiveInSeconds();
        }
        return null;
    }
}
