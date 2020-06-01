package com.zsinda.fdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsinda.fdp.entity.SysFile;
import com.zsinda.fdp.vo.FileVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {

    FileVo detail(Integer id, Integer tenantId);
}
