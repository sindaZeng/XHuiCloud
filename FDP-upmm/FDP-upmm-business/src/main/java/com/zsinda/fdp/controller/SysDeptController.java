package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.entity.SysDept;
import com.zsinda.fdp.service.SysDeptService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.TreeUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: FDPlatform
 * @description: SysDeptController
 * @author: Sinda
 * @create: 2020-03-21 15:48
 */
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
@Api(value = "dept",tags = "部门管理模块")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 树形菜单
     * @return
     */
    @GetMapping(value = "/tree")
    public R getDeptTree() {
        return R.ok(TreeUtil.buildDeptTree(sysDeptService
                .list(Wrappers.<SysDept>lambdaQuery()
                        .orderByAsc(SysDept::getSort)), 0));
    }
}
