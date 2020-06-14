package com.zsinda.fdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsinda.fdp.annotation.ChooseDataSource;
import com.zsinda.fdp.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@ChooseDataSource("second")
public interface SysUserMapper extends BaseMapper<SysUser> {
}
