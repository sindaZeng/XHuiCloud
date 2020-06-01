package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户部门")
@TableName(value = "sys_user_dept")
public class SysUserDept implements Serializable {

    @ApiModelProperty(value="用户Id")
    @TableId(value = "user_id", type = IdType.INPUT)
    private Integer userId;

    @ApiModelProperty(value="部门Id")
    @TableId(value = "dept_id", type = IdType.INPUT)
    private Integer deptId;

    private static final long serialVersionUID = 1L;
}
