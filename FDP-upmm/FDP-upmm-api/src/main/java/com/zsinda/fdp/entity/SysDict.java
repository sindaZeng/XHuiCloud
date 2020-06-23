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
    * 字典表
    */
@ApiModel(value="com-zsinda-fdp-entity-SysDict")
@Data
@TableName(value = "sys_dict")
public class SysDict implements Serializable {
    /**
     * 字典项主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="字典项主键")
    private Integer id;

    /**
     * 字典项类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value="字典项类型")
    private String type;

    /**
     * 字典项描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="字典项描述")
    private String description;

    /**
     * 字典项备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="字典项备注")
    private String remark;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
     * 创建者id
     */
    @TableField(value = "create_id")
    @ApiModelProperty(value="创建者id")
    private Integer createId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_id")
    @ApiModelProperty(value="更新者id")
    private Integer updateId;

    /**
     * 0: 禁用 1：启用
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value="0: 禁用 1：启用")
    private Integer delFlag;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户id")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;
}
