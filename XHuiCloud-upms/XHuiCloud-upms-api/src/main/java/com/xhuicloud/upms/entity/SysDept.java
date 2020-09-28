package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门表
 */
@Data
@ApiModel(value = "部门")
public class SysDept implements Serializable {
    /**
     * 部门id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String name;

    /**
     * 区域、地址、工位
     */
    @ApiModelProperty(value = "区域、地址、工位")
    private String address;

    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID")
    private Integer parentId;

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
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

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
