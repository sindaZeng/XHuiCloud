package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.entity.SysParam;
import com.zsinda.fdp.service.SysParamService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: FDPlatform
 * @description: SysConfigController
 * @author: Sinda
 * @create: 2020-05-09 12:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Api(value = "param", tags = "系统公共参数模块")
public class SysParamController {

    private final SysParamService sysParamService;

    /**
     * 分页查询系统参数列表
     *
     * @return
     */
    @GetMapping("/page")
    public R page(Page page) {
        return R.ok(sysParamService.page(page));
    }

    /**
     * 新增系统参数
     *
     * @param sysParam
     * @return
     */
    @SysLog("新增系统参数")
    @PostMapping("/save")
    @PreAuthorize("@authorize.hasPermission('sys_add_param')")
    public R save(@Valid @RequestBody SysParam sysParam) {
        return R.ok(sysParamService.saveParam(sysParam));
    }

    /**
     * 获取一个系统参数
     *
     * @return
     */
    @Inner(value = false)
    @GetMapping("/get")
    public R<SysParam> get(@RequestParam String key) {
        return R.ok(sysParamService.getSysConfigByKey(key));
    }

    /**
     * 更新系统参数
     *
     * @param sysParam
     * @return
     */
    @SysLog("更新系统参数")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_param')")
    public R update(@Valid @RequestBody SysParam sysParam) {
        sysParam.setUpdateId(SpringSecurityUtils.getUserId());
        return R.ok(sysParamService.updateById(sysParam));
    }

    /**
     * 删除系统参数
     *
     * @param id
     * @return
     */
    @SysLog("删除系统参数")
    @PreAuthorize("@authorize.hasPermission('sys_delete_param')")
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysParamService.removeById(id));
    }
}


