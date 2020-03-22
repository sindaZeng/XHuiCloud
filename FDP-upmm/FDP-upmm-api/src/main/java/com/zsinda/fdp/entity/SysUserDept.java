package com.zsinda.fdp.entity;

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
    private Integer userId;

    @ApiModelProperty(value="部门Id")
    private Integer deptId;

    private static final long serialVersionUID = 1L;
}
