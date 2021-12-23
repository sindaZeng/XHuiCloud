package com.xhuicloud.upms.dto;

import com.xhuicloud.upms.entity.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: XHuiCloud
 * @description: UserInfo
 * @author: Sinda
 * @create: 2019-12-26 00:23
 **/
@Data
public class UserInfo implements Serializable {

    /**
     * 用户基本信息
     */
    private SysUser sysUser;
    /**
     * 权限标识集合
     */
    private String[] permissions;
    /**
     * 角色集合
     */
    private String[] roles;
    /**
     * 租户名称
     */
    private String tenantName;
}
