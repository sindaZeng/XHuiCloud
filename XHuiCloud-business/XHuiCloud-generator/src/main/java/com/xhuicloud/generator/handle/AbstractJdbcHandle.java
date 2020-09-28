package com.xhuicloud.generator.handle;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xhuicloud.common.datasource.dds.DynamicDataSource;
import com.xhuicloud.generator.dto.GenCodeDto;
import com.xhuicloud.generator.entity.GenDatasourceInfo;
import com.xhuicloud.generator.entity.TableColumnsInfo;
import com.xhuicloud.generator.entity.TableInfo;
import com.xhuicloud.generator.utils.GenCodeUtil;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
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

    private final DataSource dataSource;

    @Override
    public Boolean addDynamicDataSource(GenDatasourceInfo datasourceInfo) {
        if (!test(datasourceInfo)) {
            return Boolean.FALSE;
        }
        DynamicDataSource dynamicDataSource = (DynamicDataSource) dataSource;
        dynamicDataSource.putDataSourceMap("GEN", createDataSource(datasourceInfo));
        return Boolean.TRUE;
    }

    @Override
    public byte[] genCode(GenCodeDto genCodeDto) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        genCodeDto.getTableName().forEach(name -> {
            TableInfo tableInfo = getTableInfo(name);
            if (ObjectUtil.isNotEmpty(tableInfo)){
                List<TableColumnsInfo> tableColumnsInfo = getTableColumnsInfo(name);
                GenCodeUtil.get(genCodeDto,tableInfo,tableColumnsInfo,zip);
            }
        });
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }
}
