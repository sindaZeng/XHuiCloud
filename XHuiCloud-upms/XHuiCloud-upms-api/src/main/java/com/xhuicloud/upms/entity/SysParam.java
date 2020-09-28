package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 参数配置表
 */
@Data
@ApiModel(value = "参数配置")
public class SysParam implements Serializable {
    /**
     * 参数主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "参数主键")
    private Integer id;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String paramName;

    /**
     * 参数键名
     */
    @ApiModelProperty(value = "参数键名")
    private String paramKey;

    /**
     * 参数键值
     */
    @ApiModelProperty(value = "参数键值")
    private String paramValue;

    /**
     * 0:系统参数  1:业务参数
     */
    @ApiModelProperty(value = "0:系统参数  1:业务参数")
    private Integer paramType;

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
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Integer updateId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value = "0:否 1:是")
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}
