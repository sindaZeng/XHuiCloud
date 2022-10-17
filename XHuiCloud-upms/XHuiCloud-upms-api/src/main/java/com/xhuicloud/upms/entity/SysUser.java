/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xhuicloud.common.core.annotation.Excel;
import com.xhuicloud.common.core.annotation.Scalpel;
import com.xhuicloud.common.core.data.ScalpelTypeEnum;
import com.xhuicloud.common.core.enums.excel.ColumnType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户表
 */
@Data
@ApiModel(value = "系统用户")
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    @Excel(name = "用户编号", cellType = ColumnType.NUMERIC)
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @Excel(name = "用户名称")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @Excel(name = "用户头像")
    @Scalpel(type = ScalpelTypeEnum.ADD_DOMAIN)
    private String avatar;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Excel(name = "用户手机")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 性别:0 女、1  男、2  其他
     */
    @ApiModelProperty(value = "性别:0 女、1  男、2  其他")
    @Excel(name = "用户性别", readConverterExp = "女=0,男=1,其他=2")
    private Integer sex;

    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private String motto;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id", hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private Integer updateId;

    /**
     * 0:账号被锁
     */
    @ApiModelProperty(value = "0:账号被锁")
    @Excel(name = "锁定状态", readConverterExp = "已锁定=0,正常=1")
    private Integer lockFlag;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value = "0:否 1:是")
    @Excel(name = "注销状态", readConverterExp = "已注销=0,正常=1")
    private Integer isDel;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    /**
     * 角色名称
     */
    @Excel(name = "用户角色")
    @TableField(exist = false)
    private List<Integer> roleIds;

    /**
     * 角色名称
     */
    @Excel(name = "用户部门")
    @TableField(exist = false)
    private List<Integer> deptIds;

    private static final long serialVersionUID = 1L;
}
