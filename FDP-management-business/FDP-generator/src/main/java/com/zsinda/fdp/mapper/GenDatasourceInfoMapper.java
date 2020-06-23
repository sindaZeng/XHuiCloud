package com.zsinda.fdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.annotation.ChooseDataSource;
import com.zsinda.fdp.entity.GenDatasourceInfo;
import com.zsinda.fdp.entity.TableColumnsInfo;
import com.zsinda.fdp.entity.TableInfo;
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
    @ChooseDataSource("GEN")
    TableInfo queryTableForMysql(@Param("tableName") String tableName);

    /**
     * 分页查询表信息
     *
     * @param page
     * @return
     */
    @ChooseDataSource("GEN")
    IPage<List<TableInfo>> queryPageTableForMysql(@Param("page") Page page);

    /**
     * 查询表字段信息
     *
     * @param tableName
     * @return
     */
    @ChooseDataSource("GEN")
    List<TableColumnsInfo> queryColumnsForMysql(@Param("tableName") String tableName);
}
