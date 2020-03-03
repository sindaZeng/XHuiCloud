package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysRouteConf;

import java.util.List;

public interface SysRouteConfService extends IService<SysRouteConf>{

    /**
     * 获取全部路由
     *
     * @return
     */
    List<SysRouteConf> getRoutes();

}
