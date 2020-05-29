package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 参数配置表
 */
@Data
@ApiModel(value = "参数配置")
public class SysParam implements Serializable {
    /**
     * 参数主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "参数主键")
    private Integer id;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @ApiModelProperty(value = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @ApiModelProperty(value = "参数键值")
    private String configValue;

    /**
     * 0:系统参数  1:业务参数
     */
    @ApiModelProperty(value = "0:系统参数  1:业务参数")
    private Integer configType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id")
    private Integer createId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Integer updateId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 0: 禁用 1：启用
     */
    @ApiModelProperty(value = "0: 禁用 1：启用")
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}
