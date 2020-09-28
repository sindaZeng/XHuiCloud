package com.xhuicloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhuicloud.upms.entity.SysMenu;
import com.xhuicloud.upms.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<MenuVO> listMenusByRoleId(Integer roleId);

    void deleteMenu(@Param("ids") List<Integer> ids);
}
