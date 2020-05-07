package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.entity.SysDept;
import com.zsinda.fdp.service.SysDeptService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.TreeUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: FDPlatform
 * @description: SysDeptController
 * @author: Sinda
 * @create: 2020-03-21 15:48
 */
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
@Api(value = "dept", tags = "部门管理模块")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 树形部门
     *
     * @return
     */
    @GetMapping(value = "/tree")
    public R getDeptTree() {
        return R.ok(TreeUtil.buildDeptTree(sysDeptService
                .list(Wrappers.<SysDept>lambdaQuery()
                        .orderByAsc(SysDept::getSort)), 0));
    }

    /**
     * 新增部门
     *
     * @param sysMenu
     * @return
     */
    @SysLog("新增部门")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_dept')")
    public R save(@Valid @RequestBody SysDept sysDept) {
        return R.ok(sysDeptService.saveDept(sysDept));
    }
}
