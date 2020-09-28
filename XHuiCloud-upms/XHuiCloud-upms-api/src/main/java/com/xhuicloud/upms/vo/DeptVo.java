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
public class DeptVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 部门名称
     */
    private String deptName;
}
