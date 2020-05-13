package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com-zsinda-fdp-entity-SysTenant")
@Data
@TableName(value = "sys_tenant")
public class SysTenant implements Serializable {
    /**
     * 租户id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="租户id")
    private Integer id;

    /**
     * 租户名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="租户名称")
    private String name;

    /**
     * 状态 0:禁用; 1:正常;  2:待审核;  3:拒绝
     */
    @TableField(value = "state")
    @ApiModelProperty(value="状态,0:禁用; 1:正常;  2:待审核;  3:拒绝")
    private Integer state;

    /**
     * 有效期
     */
    @TableField(value = "expiration_time")
    @ApiModelProperty(value="有效期")
    private Date expirationTime;

    /**
     * logo地址
     */
    @TableField(value = "logo")
    @ApiModelProperty(value="logo地址")
    private String logo;

    /**
     * 租户描述
     */
    @TableField(value = "remake")
    @ApiModelProperty(value="租户描述")
    private String remake;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 0: 禁用 1：启用
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value="0: 禁用 1：启用")
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}
