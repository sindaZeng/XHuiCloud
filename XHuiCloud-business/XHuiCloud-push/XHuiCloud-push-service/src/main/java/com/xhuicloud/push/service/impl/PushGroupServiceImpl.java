package com.xhuicloud.push.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.push.entity.PushGroup;
import com.xhuicloud.push.mapper.PushGroupMapper;
import com.xhuicloud.push.service.PushGroupService;
@Service
public class PushGroupServiceImpl extends ServiceImpl<PushGroupMapper, PushGroup> implements PushGroupService{

}
