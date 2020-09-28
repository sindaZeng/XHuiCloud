package com.xhuicloud.generator.handle;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.JdbcConnectConstants;
import com.xhuicloud.generator.entity.GenDatasourceInfo;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import com.xhuicloud.generator.mapper.GenDatasourceInfoMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: MysqlJdbcHandle
 * @author: Sinda
 * @create: 2020-06-22 14:33
 */
@Slf4j
@Component("MYSQL")
public class MysqlJdbcHandle extends AbstractJdbcHandle {

    @Autowired
    private GenDatasourceInfoMapper genDatasourceInfoMapper;

    public MysqlJdbcHandle(DataSource dataSource) {
        super(dataSource);
    }

    @SneakyThrows
    @Override
    public Boolean test(GenDatasourceInfo genDatasourceInfo) {
        Class.forName(JdbcConnectConstants.MYSQL_DRIVER);
        Connection connection = DriverManager.getConnection(String.format(JdbcConnectConstants.MYSQL_URL,
                genDatasourceInfo.getHost(), genDatasourceInfo.getPort(), genDatasourceInfo.getName()),
                genDatasourceInfo.getUsername(), genDatasourceInfo.getPassword());
        connection.close();
        return true;
    }

    @Override
    public TableInfo getTableInfo(String tableName) {
        return genDatasourceInfoMapper.queryTableForMysql(tableName);
    }

    @Override
    public IPage<List<TableInfo>> getPageTableInfo(Page page) {
        return genDatasourceInfoMapper.queryPageTableForMysql(page);
    }

    @Override
    public List<TableColumnsInfo> getTableColumnsInfo(String tableName) {
        return genDatasourceInfoMapper.queryColumnsForMysql(tableName);
    }

    @SneakyThrows
    @Override
    public DruidDataSource createDataSource(GenDatasourceInfo genDatasourceInfo) {
        DruidDataSource druidDataSource = new DruidDataSource();
        String URL = String.format(JdbcConnectConstants.MYSQL_URL,
                genDatasourceInfo.getHost(), genDatasourceInfo.getPort(), genDatasourceInfo.getName());
        druidDataSource.setUsername(genDatasourceInfo.getUsername());
        druidDataSource.setPassword(genDatasourceInfo.getPassword());
        druidDataSource.setUrl(URL);
        druidDataSource.setUseGlobalDataSourceStat(true);
        druidDataSource.setDriverClassName(JdbcConnectConstants.MYSQL_DRIVER);
        return druidDataSource;
    }
}
