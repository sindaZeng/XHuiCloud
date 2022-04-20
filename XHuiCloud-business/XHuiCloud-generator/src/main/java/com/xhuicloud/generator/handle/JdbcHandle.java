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

package com.xhuicloud.generator.handle;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.datasource.entity.GenDsInfo;
import com.xhuicloud.generator.dto.GenCodeDto;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;

import java.util.List;

/**
 * @program: XHuiCloud
 * @description: JdbcHandle
 * @author: Sinda
 * @create: 2020-06-22 12:02
 */
public interface JdbcHandle {

    /**
     * 尝试连接数据库
     *
     * @param genDsInfo
     * @return
     */
    Boolean test(GenDsInfo genDsInfo);

    /**
     * 获取 表信息
     *
     * @param tableName
     * @return
     */
    TableInfo getTableInfo(String tableName);

    /**
     * 获取 表信息
     *
     * @return
     */
    List<TableInfo> getTableInfos();

    /**
     * 分页获取 表信息
     *
     * @param page
     * @return
     */
    IPage getPageTableInfo(Page page);

    /**
     * 获取 表列信息
     *
     * @param tableName
     * @return
     */
    List<TableColumnsInfo> getTableColumnsInfo(String tableName);

    /**
     * 生成代码
     */
    byte[] genCode(GenCodeDto genCodeDto);

}
