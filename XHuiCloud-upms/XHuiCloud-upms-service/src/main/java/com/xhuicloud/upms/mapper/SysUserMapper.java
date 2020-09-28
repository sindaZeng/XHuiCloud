package com.xhuicloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.upms.dto.UserDto;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<List<UserVo>> userPage(Page page, @Param("query") UserDto userDto);


}
