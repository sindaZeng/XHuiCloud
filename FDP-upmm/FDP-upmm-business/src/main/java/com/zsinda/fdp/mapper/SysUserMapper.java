package com.zsinda.fdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.dto.UserDto;
import com.zsinda.fdp.entity.SysUser;
import com.zsinda.fdp.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<List<UserVo>> userPage(Page page, @Param("query") UserDto userDto);


}
