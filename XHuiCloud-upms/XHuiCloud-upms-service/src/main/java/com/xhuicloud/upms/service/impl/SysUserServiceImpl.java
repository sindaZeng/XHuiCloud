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

package com.xhuicloud.upms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.common.log.utils.LogRecordContext;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.dto.UserQueryDto;
import com.xhuicloud.upms.entity.SysMenu;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.entity.SysRole;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.mapper.SysUserMapper;
import com.xhuicloud.upms.service.*;
import com.xhuicloud.upms.vo.UserVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xhuicloud.common.core.constant.SysParamConstants.*;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysRoleService sysRoleService;

    private final SysUserRoleService sysUserRoleService;

    private final SysMenuService sysMenuService;

    private final SysParamService sysParamService;

    private final SysDeptService sysDeptService;

    private final SysUserDeptService sysUserDeptService;

    private final SysTenantService sysTenantService;

    @Override
    public UserInfo getSysUser(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //查询该用户的角色
        List<SysRole> sysRoles = sysRoleService.findRoleById(sysUser.getUserId());
        List<Long> roleIds = sysRoles.stream().map(SysRole::getId)
                .collect(Collectors.toList());
        List<String> roleCodes = sysRoles.stream().map(SysRole::getRoleCode)
                .collect(Collectors.toList());

        userInfo.setRoles(ArrayUtil.toArray(roleCodes, String.class));

        //设置权限列表（menu.permission）
        Set<String> permissions = new HashSet<>();

        roleIds.forEach(roleId -> {
            List<SysMenu> sysMenus = sysMenuService.findMenuByRoleId(roleId);

            List<String> permissionList = sysMenus
                    .stream()
                    .filter(menu -> StringUtils.isNotEmpty(menu.getPermission()))
                    .map(SysMenu::getPermission)
                    .collect(Collectors.toList());

            List<String> urlList = sysMenus
                    .stream()
                    .filter(menu -> StringUtils.isNotEmpty(menu.getPermission()) && StringUtils.isNotEmpty(menu.getUrl()))
                    .map(SysMenu::getUrl)
                    .collect(Collectors.toList());

            permissions.addAll(permissionList);
            permissions.addAll(urlList);
        });
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        userInfo.setTenantName(sysTenantService.getById(sysUser.getTenantId()).getName());
        return userInfo;
    }

    @Override
    public IPage<UserVo> userPage(Page page, UserQueryDto userQueryDto) {
        return baseMapper.userPage(page, userQueryDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean lock(Long id) {
        SysUser sysUser = checkUserId(id);
        if (sysUser.getLockFlag() == 0) {
            sysUser.setLockFlag(1);
        } else {
            sysUser.setLockFlag(0);
        }
        return updateById(sysUser);
    }

    /**
     * 此处不开启事务，能插入的都插入。不能插入的返回提示给前端
     *
     * @param userList      用户集合
     * @param updateSupport 是否更新已存在的用户
     * @return
     */
    @Override
    public String importUser(List<SysUser> userList, boolean updateSupport) {
        if (CollectionUtils.isEmpty(userList)) {
            SysException.sysFail("导入用户数据不能为空!");
        }
        // 系统默认密码配置
        SysParam sysParamPassWord = sysParamService.getSysParamByKey(SYS_USER_DEFAULT_PASSWORD);
        // 系统默认角色配置
        SysParam sysParamRole = sysParamService.getSysParamByKey(SYS_USER_DEFAULT_ROLE);
        // 系统默认部门
        SysParam sysParamDept = sysParamService.getSysParamByKey(SYS_USER_DEFAULT_DEPT);
        // 所有的部门id
        List<Long> allDeptIds = sysDeptService.getAllDeptIds();
        // 所有的角色id
        List<Long> allRoleIds = sysRoleService.getAllRoleIds();
        // 成功总数
        int successNum = 0;
        // 失败总数
        int failureNum = 0;
        // 成功导入信息
        StringBuilder successMsg = new StringBuilder();
        // 失败导入信息
        StringBuilder failureMsg = new StringBuilder();

        for (SysUser sysUser : userList) {
            try {
                // 用户部门
                List<Long> deptIds = getDeptIds(allDeptIds, sysUser.getDeptIds(), sysParamDept);
                // 用户角色
                List<Long> roleIds = getRoleIds(allRoleIds, sysUser.getRoleIds(), sysParamRole);
                SysUser user = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, sysUser.getUsername()));
                if (ObjectUtils.isEmpty(user)) {
                    sysUser.setPassword(sysParamPassWord.getParamValue());
                    saveUserAndRoleAndDept(sysUser, deptIds, roleIds);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + sysUser.getUsername() + " 导入成功");
                } else if (updateSupport) {
                    // 已存在的用户 是否更新信息
                    // 原id不变，防止导入时用户id不对应
                    sysUser.setUserId(user.getUserId());
                    // 创建时间不变
                    sysUser.setCreateTime(user.getCreateTime());
                    updateUserAndRoleAndDept(sysUser, deptIds, roleIds);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + sysUser.getUsername() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + sysUser.getUsername() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + sysUser.getUsername() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            SysException.sysFail(failureMsg.toString());
        } else {
            successMsg.insert(0, "数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    @AuditRecord(value = "#detail + '原角色:' + #oldRole + '新角色:' + #newRole")
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(SysUser sysUser) {
        SysUser user = getSysUser(sysUser.getUserId());
        String detail = String.format("%s修改了%s(%s)的用户信息;", SecurityHolder.getOperator(), user.getUsername(), user.getUserId());
        if (CollectionUtil.isNotEmpty(sysUser.getDeptIds())) {
            //删除该用户下的所有部门，重新插入
            sysUserDeptService.updateUserDept(user.getUserId(), sysUser.getDeptIds());

        }
        if (CollectionUtil.isNotEmpty(sysUser.getRoleIds())) {
            List<SysRole> sysRoles = sysRoleService.findRoleById(sysUser.getUserId());
            List<String> roleCodes = sysRoles.stream().map(SysRole::getRoleCode)
                    .collect(Collectors.toList());
            LogRecordContext.putVariable("oldRole", roleCodes);
            //删除该用户下的所有部门，重新插入
            sysUserRoleService.updateUserRole(user.getUserId(), sysUser.getRoleIds());
            sysRoles = sysRoleService.findRoleById(sysUser.getUserId());
            roleCodes = sysRoles.stream().map(SysRole::getRoleCode)
                    .collect(Collectors.toList());
            LogRecordContext.putVariable("newRole", roleCodes);
        }
        LogRecordContext.putVariable("detail", detail);
        return updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveUser(SysUser sysUser) {
        try {
            // 设置默认密码
            if (StringUtils.isBlank(sysUser.getPassword())) {
                // 系统默认密码配置
                SysParam sysParamPassWord = sysParamService.getSysParamByKey(SYS_USER_DEFAULT_PASSWORD);
                sysUser.setPassword(sysParamPassWord.getParamValue());
            } else {
                sysUser.setPassword(SecurityHolder.encoder(sysUser.getPassword()));
            }
            // 所有的部门id
            List<Long> allDeptIds = sysDeptService.getAllDeptIds();
            // 所有的角色id
            List<Long> allRoleIds = sysRoleService.getAllRoleIds();
            // 系统默认角色配置
            SysParam sysParamRole = sysParamService.getSysParamByKey(SYS_USER_DEFAULT_ROLE);
            // 系统默认部门
            SysParam sysParamDept = sysParamService.getSysParamByKey(SYS_USER_DEFAULT_DEPT);
            // 用户部门
            List<Long> deptIds = getDeptIds(allDeptIds, sysUser.getDeptIds(), sysParamDept);
            // 用户角色
            List<Long> roleIds = getRoleIds(allRoleIds, sysUser.getRoleIds(), sysParamRole);
            return saveUserAndRoleAndDept(sysUser, deptIds, roleIds);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw SysException.fail(SysException.USER_IS_EXIST_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public Boolean updateUserMotto(String motto) {
        SysUser user = getSysUser(SecurityHolder.getUserId());
        user.setMotto(motto);
        return updateById(user);
    }

    @Override
    public Boolean updateUserAvatar(String avatar) {
        SysUser user = getSysUser(SecurityHolder.getUserId());
        user.setAvatar(avatar);
        return updateById(user);
    }

    @Override
    public Boolean updateUserPhone(String mobile) {
        if (count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, mobile)) > 0) {
            SysException.sysFail(SysException.MOBILE_IS_ALREADY_BOUND);
        }
        SysUser user = getSysUser(SecurityHolder.getUserId());
        user.setPhone(mobile);
        return updateById(user);
    }

    private SysUser getSysUser(Long userId) {
        SysUser user = getById(userId);
        if (ObjectUtil.isEmpty(user)) {
            SysException.sysFail(SysException.USER_NOT_EXIST_DATA_EXCEPTION);
        }
        return user;
    }

    /**
     * 部门去重
     * 去无效部门
     * 如果为空 补充默认部门
     *
     * @param allDeptIds
     * @param userDeptIds
     * @param sysParamDept
     * @return
     */
    private List<Long> getDeptIds(List<Long> allDeptIds, List<Long> userDeptIds, SysParam sysParamDept) {
        return deduplication(allDeptIds, userDeptIds, sysParamDept);
    }

    /**
     * 角色去重
     * 去无效角色
     * 如果为空 补充默认角色
     *
     * @param allRoleIds
     * @param userRoleIds
     * @param sysParamRole
     * @return
     */
    private List<Long> getRoleIds(List<Long> allRoleIds, List<Long> userRoleIds, SysParam sysParamRole) {
        return deduplication(allRoleIds, userRoleIds, sysParamRole);
    }

    private List<Long> deduplication(List<Long> allIds, List<Long> ids, SysParam sysParam) {
        if (CollectionUtil.isNotEmpty(ids)) {
            return ids.stream()
                    .distinct().filter(id -> allIds.contains(id)).collect(Collectors.toList());
        } else {
            ids = new ArrayList(1);
            ids.add(Long.valueOf(sysParam.getParamValue()));
            return ids;
        }
    }

    /**
     * 保存用户 角色 和 部门
     *
     * @param sysUser
     * @param deptIds
     * @param roleIds
     */
    private Long saveUserAndRoleAndDept(SysUser sysUser, List<Long> deptIds, List<Long> roleIds) {
        // 新增用户
        baseMapper.insert(sysUser);
        // 新增用户角色
        sysUserRoleService.saveUserRole(sysUser.getUserId(), roleIds);
        // 新增用户部门
        sysUserDeptService.saveUserDept(sysUser.getUserId(), deptIds);
        return sysUser.getUserId();
    }


    private void updateUserAndRoleAndDept(SysUser sysUser, List<Long> deptIds, List<Long> roleIds) {
        // 更新用户
        updateUser(sysUser);
        // 更新用户角色
        sysUserRoleService.updateUserRole(sysUser.getUserId(), roleIds);
        // 新增用户部门
        sysUserDeptService.updateUserDept(sysUser.getUserId(), deptIds);
    }

    /**
     * 校验用户id 是否存在
     *
     * @param id
     * @return
     */
    private SysUser checkUserId(Long id) {
        SysUser sysUser = getById(id);
        if (sysUser == null) {
            SysException.sysFail(SysException.USER_NOT_EXIST_DATA_EXCEPTION);
        }
        return sysUser;
    }


}
