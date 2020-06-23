package com.zsinda.fdp.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zsinda.fdp.dto.GenCodeDto;
import com.zsinda.fdp.entity.GenDatasourceInfo;
import com.zsinda.fdp.handle.JdbcHandle;
import com.zsinda.fdp.mapper.GenDatasourceInfoMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * @program: FDPlatform
 * @description:
 * @author: Sinda
 * @create: 2020-06-22 11:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/code")
public class CodeGeneratorController {

    private final GenDatasourceInfoMapper genDatasourceInfoMapper;

    private final Map<String, JdbcHandle> handle;

    /**
     * 代码生成
     * 数据库名 需要生成的表名。必须严格传入此库拥有的表
     */
    @SneakyThrows
    @PostMapping("/{dataSourceId}/generator")
    public void generator(@PathVariable Integer dataSourceId,
                          @Valid @RequestBody GenCodeDto genCodeDto,
                          HttpServletResponse response) {
        GenDatasourceInfo genDatasourceInfo = genDatasourceInfoMapper.selectById(dataSourceId);
        JdbcHandle jdbcHandle = handle.get(genDatasourceInfo.getType());
        if (ObjectUtil.isNotNull(jdbcHandle) && jdbcHandle.addDynamicDataSource(genDatasourceInfo)) {
            byte[] data = jdbcHandle.genCode(genCodeDto);
            response.reset();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", genCodeDto.getModuleName()));
            response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
            response.setContentType("application/octet-stream; charset=UTF-8");
            IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
        }
    }
}
