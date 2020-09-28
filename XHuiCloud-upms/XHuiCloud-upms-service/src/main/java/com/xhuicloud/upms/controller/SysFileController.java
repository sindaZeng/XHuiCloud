package com.xhuicloud.upms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.R;
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
    public R page(Page page, SysFile sysFile) {
        return R.ok(sysFileService.page(page,
                Wrappers.lambdaQuery(sysFile)));
    }

    /**
     * 七牛文件上传
     *
     * @param file
     * @return
     */
    @SysLog("上传文件")
    @PostMapping("/upload/{fileType}")
    @PreAuthorize("@authorize.hasPermission('sys_upload_file')")
    public R upload(@RequestParam("file") MultipartFile file, @PathVariable("fileType") String fileType) {
        return R.ok(sysFileService.upload(file, fileType));
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
    public R delete(@PathVariable Integer id) {
        return R.ok(sysFileService.deleteFileById(id));
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
    public R detail(@PathVariable Integer id) {
       return R.ok(sysFileService.detail(id));
    }

}
