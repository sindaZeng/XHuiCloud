package com.xhuicloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhuicloud.upms.entity.SysFile;
import com.xhuicloud.upms.vo.FileVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {

    FileVo detail(Integer id, Integer tenantId);
}
