package com.xhuicloud.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.upms.entity.SysDictData;
import com.xhuicloud.upms.mapper.SysDictDataMapper;
import com.xhuicloud.upms.service.SysDictDataService;
import org.springframework.stereotype.Service;

@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public IPage dictDataPage(SysDictData dictDataDto, Page page) {
        return page(page, Wrappers.lambdaQuery(dictDataDto));
    }
}
