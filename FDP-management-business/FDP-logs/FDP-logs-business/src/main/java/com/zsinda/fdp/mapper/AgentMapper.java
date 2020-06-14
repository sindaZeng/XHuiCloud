package com.zsinda.fdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsinda.fdp.annotation.ChooseDataSource;
import com.zsinda.fdp.entity.Agent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@ChooseDataSource("first")
public interface AgentMapper extends BaseMapper<Agent> {
}
