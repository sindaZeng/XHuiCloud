package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="用户部门")
public class SysUserDept extends Model<SysUserDept> {

    @ApiModelProperty(value="用户Id")
    private Integer userId;

    @ApiModelProperty(value="部门Id")
    private Integer deptId;

}
