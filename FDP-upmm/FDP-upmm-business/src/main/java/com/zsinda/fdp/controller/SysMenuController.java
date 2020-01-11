package com.zsinda.fdp.controller;

import com.zsinda.fdp.dto.MenuTree;
import com.zsinda.fdp.service.SysMenuService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import com.zsinda.fdp.utils.TreeUtil;
import com.zsinda.fdp.vo.MenuVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: FDPlatform
 * @description: SysMenuController
 * @author: Sinda
 * @create: 2020-01-02 23:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 返回当前用户的树形菜单集合
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public R getUserMenu() {
        // 获取符合条件的菜单
        String MENU="0";
        Set<MenuVO> all = new HashSet<>();
        SpringSecurityUtils.getRoles()
                .forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> MENU.equals(menuVo.getType()))
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());


        return R.ok(TreeUtil.build(menuTreeList,0));
    }

}
