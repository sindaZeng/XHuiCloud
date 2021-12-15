package com.xhuicloud.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.generator.entity.GenDatasourceInfo;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenDatasourceInfoMapper extends BaseMapper<GenDatasourceInfo> {

    /**
     * 查询表信息
     *
     * @param tableName 表名称
     * @return
     */
    TableInfo queryTableForMysql(@Param("tableName") String tableName);

    /**
     * 分页查询表信息
     *
     * @param page
     * @return
     */
    IPage<List<TableInfo>> queryPageTableForMysql(@Param("page") Page page);

    /**
     * 查询表字段信息
     *
     * @param tableName
     * @return
     */
    List<TableColumnsInfo> queryColumnsForMysql(@Param("tableName") String tableName);
}
