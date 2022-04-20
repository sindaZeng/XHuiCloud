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

package com.xhuicloud.generator.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.xhuicloud.common.datasource.entity.GenDsInfo;
import com.xhuicloud.generator.dto.GenCodeDto;
import com.xhuicloud.generator.handle.JdbcHandle;
import com.xhuicloud.generator.service.GenDsInfoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/generator")
@Api(value = "generator", tags = "代码生成")
public class GenCodeController {

    private final GenDsInfoService genDsInfoService;

    private final Map<String, JdbcHandle> handle;

    /**
     * 代码生成
     * 数据库名 需要生成的表名。必须严格传入此库拥有的表
     */
    @SneakyThrows
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_download_code')")
    public void generator(@Valid @RequestBody GenCodeDto genCodeDto,
                          HttpServletResponse response) {
        GenDsInfo genDsInfo = genDsInfoService.getById(genCodeDto.getId());
        JdbcHandle jdbcHandle = handle.get(genDsInfo.getType());
        if (ObjectUtil.isNotNull(jdbcHandle) && jdbcHandle.test(genDsInfo)) {
            DynamicDataSourceContextHolder.push(genDsInfo.getName());
            byte[] data = jdbcHandle.genCode(genCodeDto);
            response.reset();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.zip", genCodeDto.getModuleName()));
            response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
            response.setContentType("application/octet-stream; charset=UTF-8");
            DynamicDataSourceContextHolder.clear();
            IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
        }
    }

}
