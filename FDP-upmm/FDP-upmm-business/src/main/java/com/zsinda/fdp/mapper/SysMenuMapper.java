package com.zsinda.fdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsinda.fdp.entity.SysMenu;
import com.zsinda.fdp.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<MenuVO> listMenusByRoleId(Integer roleId);

    void deleteMenu(@Param("ids") List<Integer> ids);
}
