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

@ApiModel(value="com-zsinda-fdp-entity-SysConfig")
@Data
@TableName(value = "sys_config")
public class SysConfig implements Serializable {
    /**
     * 参数主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="参数主键")
    private Integer id;

    /**
     * 参数名称
     */
    @TableField(value = "config_name")
    @ApiModelProperty(value="参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @TableField(value = "config_key")
    @ApiModelProperty(value="参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @TableField(value = "config_value")
    @ApiModelProperty(value="参数键值")
    private String configValue;

    /**
     * 0:系统参数  1:业务参数
     */
    @TableField(value = "config_type")
    @ApiModelProperty(value="0:系统参数  1:业务参数")
    private Integer configType;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 创建者id
     */
    @TableField(value = "create_id")
    @ApiModelProperty(value="创建者id")
    private String createId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_id")
    @ApiModelProperty(value="更新者id")
    private Integer updateId;

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
