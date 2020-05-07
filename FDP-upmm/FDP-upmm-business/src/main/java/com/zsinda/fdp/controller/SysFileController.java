package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.entity.SysFile;
import com.zsinda.fdp.service.SysFileService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FDPlatform
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
    public R page(Page page) {
        return R.ok(sysFileService.page(page, Wrappers.<SysFile>lambdaQuery().eq(SysFile::getDelFlag, 1)));
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
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysFileService.deleteFileById(id));
    }

}
