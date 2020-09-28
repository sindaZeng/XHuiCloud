package com.xhuicloud.generator.handle;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.generator.dto.GenCodeDto;
import com.xhuicloud.generator.entity.GenDatasourceInfo;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;

import java.util.List;

/**
 * @program: XHuiCloud
 * @description: JdbcHandle
 * @author: Sinda
 * @create: 2020-06-22 12:02
 */
public interface JdbcHandle {

    /**
     * 尝试连接数据库
     *
     * @param genDatasourceInfo
     * @return
     */
    Boolean test(GenDatasourceInfo genDatasourceInfo);

    /**
     * 获取 表信息
     *
     * @param tableName
     * @return
     */
    TableInfo getTableInfo(String tableName);

    /**
     * 分页获取 表信息
     *
     * @param page
     * @return
     */
    IPage<List<TableInfo>> getPageTableInfo(Page page);

    /**
     * 获取 表列信息
     *
     * @param tableName
     * @return
     */
    List<TableColumnsInfo> getTableColumnsInfo(String tableName);

    /**
     * 添加数据源到 动态数据源列表
     *
     * @param genDatasourceInfo
     * @return
     */
    Boolean addDynamicDataSource(GenDatasourceInfo genDatasourceInfo);

    /**
     * 创建数据源
     *
     * @return
     */
    DruidDataSource createDataSource(GenDatasourceInfo genDatasourceInfo);

    /**
     * 生成代码
     */
    byte[] genCode(GenCodeDto genCodeDto);

}
