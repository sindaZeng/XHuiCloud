package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysDictData;

public interface SysDictDataService extends IService<SysDictData> {

    IPage dictDataPage(SysDictData dictDataDto, Page page);
}
