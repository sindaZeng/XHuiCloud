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

package com.xhuicloud.generator.handle;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.JdbcConnectConstants;
import com.xhuicloud.common.core.exception.ValidateException;
import com.xhuicloud.common.datasource.entity.GenDsInfo;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import com.xhuicloud.generator.mapper.GenDsInfoMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: MysqlJdbcHandle
 * @author: Sinda
 * @create: 2020-06-22 14:33
 */
@Slf4j
@Component("mysql")
@AllArgsConstructor
public class MysqlJdbcHandle extends AbstractJdbcHandle {

    private final GenDsInfoMapper genDsInfoMapper;

    @SneakyThrows
    @Override
    public Boolean test(GenDsInfo genDsInfo) {
        try {
            Connection connection = DriverManager.getConnection(String.format(JdbcConnectConstants.MYSQL_URL,
                            genDsInfo.getHost(), genDsInfo.getPort(), genDsInfo.getName()),
                    genDsInfo.getUsername(), genDsInfo.getPassword());
            connection.close();
        } catch (SQLException e) {
            throw ValidateException.validateFail("用户名或者密码错误!");
        }

        return true;
    }

    @Override
    public TableInfo getTableInfo(String tableName) {
        return genDsInfoMapper.queryTableForMysql(tableName);
    }

    @Override
    public List<TableInfo> getTableInfos() {
        return genDsInfoMapper.queryPageTableForMysql();
    }

    @Override
    public IPage getPageTableInfo(Page page) {
        return genDsInfoMapper.queryPageTableForMysql(page);
    }

    @Override
    public List<TableColumnsInfo> getTableColumnsInfo(String tableName) {
        return genDsInfoMapper.queryColumnsForMysql(tableName);
    }

}

