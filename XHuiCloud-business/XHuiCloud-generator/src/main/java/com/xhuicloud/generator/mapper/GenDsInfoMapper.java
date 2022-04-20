package com.xhuicloud.generator.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.datasource.entity.GenDsInfo;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenDsInfoMapper extends BaseMapper<GenDsInfo> {

    /**
     * 查询表信息
     *
     * @param tableName 表名称
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    TableInfo queryTableForMysql(@Param("tableName") String tableName);

    /**
     * 表信息列表
     *
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    List<TableInfo> queryPageTableForMysql();

    /**
     * 分页查询表信息
     *
     * @param page
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    IPage<TableInfo> queryPageTableForMysql(@Param("page") Page page);

    /**
     * 查询表字段信息
     *
     * @param tableName
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    List<TableColumnsInfo> queryColumnsForMysql(@Param("tableName") String tableName);
}
