package com.xhuicloud.logs.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.logs.mapper.SysLogMapper;
import com.xhuicloud.logs.entity.SysLog;
import com.xhuicloud.logs.service.SysLogService;
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService{

}
