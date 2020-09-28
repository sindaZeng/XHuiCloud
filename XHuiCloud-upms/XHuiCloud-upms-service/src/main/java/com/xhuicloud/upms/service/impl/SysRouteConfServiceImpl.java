package com.xhuicloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.upms.entity.SysRouteConf;
import com.xhuicloud.upms.mapper.SysRouteConfMapper;
import com.xhuicloud.upms.service.SysRouteConfService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRouteConfServiceImpl extends ServiceImpl<SysRouteConfMapper, SysRouteConf> implements SysRouteConfService {

    @Override
    public List<SysRouteConf> getRoutes() {
        return list(Wrappers.<SysRouteConf>lambdaQuery().eq(SysRouteConf::getIsDel,1));
    }

}
