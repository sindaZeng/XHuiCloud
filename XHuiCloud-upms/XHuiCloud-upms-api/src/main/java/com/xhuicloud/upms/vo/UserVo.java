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

package com.xhuicloud.upms.vo;

import com.xhuicloud.common.core.annotation.Scalpel;
import com.xhuicloud.common.core.data.ScalpelTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: UserVo
 * @author: Sinda
 * @create: 2020-03-17 15:47
 */
@Data
@ApiModel(value = "用户列表")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "主键")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
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
    @Scalpel(type = ScalpelTypeEnum.ADD_DOMAIN)
    private String avatar;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 性别:0 女、1  男、2  其他
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 0:账号被锁
     */
    @ApiModelProperty(value = "0:账号被锁")
    private Integer lockFlag;

    /**
     * 0:账号已注销
     */
    @ApiModelProperty(value = "0:账号已注销")
    private Integer isDel;

//    /**
//     * 角色集
//     */
//    @ApiModelProperty(value = "角色集")
//    private List<RoleVo> roleVos;

    /**
     * 角色集
     */
    @ApiModelProperty(value = "角色集")
    private List<Long> roleIds;

//    /**
//     * 部门集
//     */
//    @ApiModelProperty(value = "部门集")
//    private List<DeptVo> deptVos;

    /**
     * 部门集
     */
    @ApiModelProperty(value = "部门集")
    private List<Long> deptIds;

}
