package com.zsinda.fdp.dto;

import com.zsinda.fdp.entity.SysRole;
import lombok.Data;

/**
 * @program: FDPlatform
 * @description: RoleDto
 * @author: Sinda
 * @create: 2020-03-27 11:13
 */
@Data
public class RoleDto {

    /**
     * 角色标签
     */
    private String label;

    /**
     * 角色值
     */
    private int value;

    public RoleDto(SysRole sysRole) {
        this.label = sysRole.getRoleName();
        this.value = sysRole.getRoleId();
    }
}
