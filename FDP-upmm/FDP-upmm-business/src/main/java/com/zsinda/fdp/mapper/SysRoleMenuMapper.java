package com.zsinda.fdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsinda.fdp.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    Boolean deleteRoleMenus(@Param("ids") List<Integer> ids);
}
