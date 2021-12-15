package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 字典表
 */
@Data
@ApiModel(value="字典")
public class SysDictData extends Model<SysDictData> {

    /**
     * 字典项id
     */
    @ApiModelProperty(value="字典项id")
    private Integer dictId;

    /**
     * 字典项名称
     */
    @ApiModelProperty(value="字典项名称")
    private String type;

    /**
     * 字典标签
     */
    @ApiModelProperty(value="字典标签")
    private String label;

    /**
     * 字典键值
     */
    @ApiModelProperty(value="字典键值")
    private String value;

    /**
     * 字典描述
     */
    @ApiModelProperty(value="字典描述")
    private String description;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarks;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 字典数据id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="字典数据id")
    private Integer id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id", hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private Integer updateId;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value="0:否 1:是")
    private Integer isDel;

}
