/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.generator.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.core.utils.AesUtil;
import com.xhuicloud.common.datasource.entity.GenDsInfo;
import com.xhuicloud.common.datasource.enums.DsJdbcUrlEnum;
import com.xhuicloud.generator.entity.TableInfo;
import com.xhuicloud.generator.mapper.GenDsInfoMapper;
import com.xhuicloud.generator.service.GenDsInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@AllArgsConstructor
public class GenDsInfoServiceImpl extends ServiceImpl<GenDsInfoMapper, GenDsInfo> implements GenDsInfoService {

    private final DataSourceCreator druidDataSourceCreator;

    @Override
    public Boolean addDynamicDataSource(GenDsInfo genDsInfo) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setPoolName(genDsInfo.getName());
        dataSourceProperty.setUrl(String.format(DsJdbcUrlEnum.get(genDsInfo.getType()).getUrl(),
                genDsInfo.getHost(), genDsInfo.getPort(), genDsInfo.getName()));
        dataSourceProperty.setUsername(genDsInfo.getUsername());
        dataSourceProperty.setPassword(genDsInfo.getPassword());
        DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
        DynamicRoutingDataSource dynamicRoutingDataSource = SpringUtil.getBean(DynamicRoutingDataSource.class);
        dynamicRoutingDataSource.addDataSource(dataSourceProperty.getPoolName(), dataSource);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateDynamicDataSource(GenDsInfo genDsInfo) {
        GenDsInfo _genDsInfo = getById(genDsInfo.getId());
        if (_genDsInfo == null) {
            throw SysException.sysFail("数据不存在");
        }
        // 先移除
        DynamicRoutingDataSource dynamicRoutingDataSource = SpringUtil.getBean(DynamicRoutingDataSource.class);
        dynamicRoutingDataSource.removeDataSource(_genDsInfo.getName());

        // 再添加
        addDynamicDataSource(genDsInfo);

        if (StrUtil.isNotBlank(genDsInfo.getPassword())) {
            _genDsInfo.setPassword(AesUtil.decrypt(genDsInfo.getPassword()));
        }
        return updateById(_genDsInfo);
    }

    @Override
    public IPage<TableInfo> queryPageTableForMysql(Page page) {
        return baseMapper.queryPageTableForMysql(page);
    }

}
