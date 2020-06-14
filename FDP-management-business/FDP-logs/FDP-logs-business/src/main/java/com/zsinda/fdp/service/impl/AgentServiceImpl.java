package com.zsinda.fdp.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.Agent;
import com.zsinda.fdp.mapper.AgentMapper;
import com.zsinda.fdp.service.AgentService;
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService{

}
