package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.R;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.annotation.Inner;
import com.xhuicloud.upms.entity.SysTenant;
import com.xhuicloud.upms.service.SysTenantService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: SysTenantController
 * @author: Sinda
 * @create: 2020-05-13 15:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "tenant", tags = "系统租户模块")
public class SysTenantController {

    private final SysTenantService sysTenantService;

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @Inner(value = false)
    @GetMapping("/list")
    public R<List<SysTenant>> list() {
        return R.ok(sysTenantService.list(Wrappers.<SysTenant>lambdaQuery()
                .eq(SysTenant::getState, 1).eq(SysTenant::getIsDel, 1)));
    }

    /**
     * 分页查询租户列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, SysTenant sysTenant) {
        return R.ok(sysTenantService.page(page,  Wrappers.query(sysTenant)));
    }

    /**
     * 添加租户
     *
     * @return
     */
    @SysLog("添加租户")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_tenant')")
    public R save(@Valid @RequestBody SysTenant sysTenant) {
        return R.ok(sysTenantService.save(sysTenant));
    }

    /**
     * 编辑租户
     *
     * @return
     */
    @SysLog("编辑租户")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_tenant')")
    public R update(@Valid @RequestBody SysTenant sysTenant) {
        return R.ok(sysTenantService.updateById(sysTenant));
    }

    /**
     * 开启禁用租户
     *
     * @param id
     * @return
     */
    @SysLog("开启/禁用租户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_tenant')")
    public R delete(@PathVariable Integer id) {
        return R.ok(sysTenantService.deleteTenant(id));
    }

    /**
     * 改变租户状态
     *
     * @param id
     * @return
     */
    @SysLog("改变租户状态")
    @GetMapping("/state")
    @PreAuthorize("@authorize.hasPermission('sys_delete_tenant')")
    public R state(@RequestParam(value = "id") Integer id, @RequestParam(value = "state") Integer state) {
        return R.ok(sysTenantService.state(id, state));
    }

}


