package com.xhuicloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhuicloud.upms.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    List<String> listDeptNameByUserId(Integer userId);
}
