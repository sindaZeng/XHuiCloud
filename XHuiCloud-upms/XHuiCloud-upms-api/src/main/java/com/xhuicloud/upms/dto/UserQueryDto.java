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

package com.xhuicloud.upms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: UserDto
 * @author: Sinda
 * @create: 2020-03-17 15:40
 */
@Data
@ApiModel(value = "用户列表条件")
public class UserQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜索内容: 用户名/手机号/邮箱")
    private String username;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "0:否 1:是 2:全部")
    private Integer isDel;

    @ApiModelProperty(value = "日期")
    private String[] createTime;

    @ApiModelProperty(value = "部门ID集合")
    private List<Integer> deptIds;

}
