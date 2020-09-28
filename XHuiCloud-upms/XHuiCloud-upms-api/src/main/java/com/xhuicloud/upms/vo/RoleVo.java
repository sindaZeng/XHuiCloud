package com.xhuicloud.upms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: XHuiCloud
 * @description: DeptVo
 * @author: Sinda
 * @create: 2020-05-14 14:55
 */
@Data
public class RoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;
}
