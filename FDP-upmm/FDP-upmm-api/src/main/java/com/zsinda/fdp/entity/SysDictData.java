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

@Data
@TableName(value = "sys_dict_data")
@ApiModel(value="com-zsinda-fdp-entity-SysDictData")
public class SysDictData implements Serializable {
    /**
     * 字典数据id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="字典数据id")
    private Integer id;

    /**
     * 字典项id
     */
    @TableId(value = "dict_id", type = IdType.INPUT)
    @ApiModelProperty(value="字典项id")
    private Integer dictId;

    /**
     * 字典项名称
     */
    @TableField(value = "type")
    @ApiModelProperty(value="字典项名称")
    private String type;

    /**
     * 字典标签
     */
    @TableField(value = "label")
    @ApiModelProperty(value="字典标签")
    private String label;

    /**
     * 字典键值
     */
    @TableField(value = "value")
    @ApiModelProperty(value="字典键值")
    private String value;

    /**
     * 字典描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="字典描述")
    private String description;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    @ApiModelProperty(value="备注")
    private String remarks;

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

    private static final long serialVersionUID = 1L;
}
