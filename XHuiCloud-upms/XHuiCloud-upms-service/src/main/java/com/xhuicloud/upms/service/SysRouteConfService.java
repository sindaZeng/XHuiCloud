package com.xhuicloud.upms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysRouteConf;

import java.util.List;

public interface SysRouteConfService extends IService<SysRouteConf> {

    /**
     * 获取全部路由
     *
     * @return
     */
    List<SysRouteConf> getRoutes();

}
