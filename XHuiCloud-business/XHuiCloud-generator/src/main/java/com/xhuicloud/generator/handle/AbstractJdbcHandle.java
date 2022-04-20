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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xhuicloud.generator.dto.GenCodeDto;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import com.xhuicloud.generator.utils.GenCodeUtil;
import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @program: XHuiCloud
 * @description: AbstractJdbcHandle
 * @author: Sinda
 * @create: 2020-06-22 14:27
 */
@AllArgsConstructor
public abstract class AbstractJdbcHandle implements JdbcHandle {

    @Override
    public byte[] genCode(GenCodeDto genCodeDto) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        if (CollectionUtil.isEmpty(genCodeDto.getTableName())) {
            getTableInfos().forEach(tableInfo -> {
                if (ObjectUtil.isNotEmpty(tableInfo)) {
                    List<TableColumnsInfo> tableColumnsInfo = getTableColumnsInfo(tableInfo.getTableName());
                    GenCodeUtil.get(genCodeDto, tableInfo, tableColumnsInfo, zip);
                }
            });
        } else {
            genCodeDto.getTableName().forEach(name -> {
                TableInfo tableInfo = getTableInfo(name);
                if (ObjectUtil.isNotEmpty(tableInfo)) {
                    List<TableColumnsInfo> tableColumnsInfo = getTableColumnsInfo(name);
                    GenCodeUtil.get(genCodeDto, tableInfo, tableColumnsInfo, zip);
                }
            });
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }
}
