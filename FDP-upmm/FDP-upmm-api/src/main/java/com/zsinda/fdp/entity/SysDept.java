package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门表
 */
@Data
@TableName(value = "sys_dept")
@ApiModel(value = "com-zsinda-fdp-entity-SysDept")
public class SysDept implements Serializable {
    /**
     * 部门id
     */
    @TableId(value = "dept_id", type = IdType.INPUT)
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    /**
     * 部门名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "部门名称")
    private String name;

    /**
     * 区域、地址、工位
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "区域、地址、工位")
    private String address;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "上级ID")
    private Integer parentId;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 0: 禁用 1：启用
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0: 禁用 1：启用")
    private Integer delFlag;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;
}
