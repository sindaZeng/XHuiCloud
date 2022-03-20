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

package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.upms.entity.SysFile;
import com.xhuicloud.upms.service.SysFileService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: XHuiCloud
 * @description: SysFileController
 * @author: Sinda
 * @create: 2020-03-26 14:04
 */
@RestController
@RequestMapping("/file")
@Api(value = "file", tags = "文件管理模块")
@AllArgsConstructor
public class SysFileController {

    private final SysFileService sysFileService;

    /**
     * 分页查询文件列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    public Response page(Page page, SysFile sysFile) {
        return Response.success(sysFileService.page(page,
                Wrappers.lambdaQuery(sysFile)));
    }

    /**
     * 七牛文件上传
     *
     * @param file
     * @return
     */
    @SysLog("上传文件")
    @PostMapping("/upload")
    @PreAuthorize("@authorize.hasPermission('sys_upload_file')")
    public Response upload(@RequestPart("file") MultipartFile file) {
        return Response.success(sysFileService.upload(file));
    }

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    @SysLog("删除文件")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_file')")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysFileService.deleteFileById(id));
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        sysFileService.download(fileName, response);
    }

    /**
     * 文件明细
     *
     * @param id
     */
    @GetMapping("/detail/{id}")
    public Response detail(@PathVariable Integer id) {
       return Response.success(sysFileService.detail(id));
    }

}
