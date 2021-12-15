package com.xhuicloud.push.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.push.mapper.PushTemplateMapper;
import com.xhuicloud.push.entity.PushTemplate;
import com.xhuicloud.push.service.PushTemplateService;

@Service
public class PushTemplateServiceImpl extends ServiceImpl<PushTemplateMapper, PushTemplate> implements PushTemplateService{

}
