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

@Data
@ApiModel(value="部门")
@TableName(value = "sys_dept")
public class SysDept implements Serializable {
    /**
     * 部门id
     */
    @TableId(value = "dept_id", type = IdType.INPUT)
    @ApiModelProperty(value="部门id")
    private Integer deptId;

    /**
     * 部门名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="部门名称")
    private String name;

    /**
     * 区域、地址、工位
     */
    @TableField(value = "address")
    @ApiModelProperty(value="区域、地址、工位")
    private String address;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value="上级ID")
    private Integer parentId;

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
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 1:开启、0:删除
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value="1:开启、0:删除")
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}
