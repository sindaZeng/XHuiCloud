package com.xhuicloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhuicloud.upms.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> listRolesByUserId(Integer userId);

    List<String> listRolesNameByUserId(Integer userId);
}
