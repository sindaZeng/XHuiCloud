package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典表
 */
@Data
@ApiModel(value = "字典")
public class SysDict implements Serializable {
    /**
     * 字典项主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "字典项主键")
    private Integer id;

    /**
     * 字典项类型
     */
    @ApiModelProperty(value = "字典项类型")
    private String type;

    /**
     * 字典项描述
     */
    @ApiModelProperty(value = "字典项描述")
    private String description;

    /**
     * 字典项备注
     */
    @ApiModelProperty(value = "字典项备注")
    private String remark;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id")
    private Integer createId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Integer updateId;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value = "0:否 1:是")
    private Integer isDel;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;
}
