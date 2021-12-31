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

package com.xhuicloud.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xhuicloud.common.core.annotation.Scalpel;
import com.xhuicloud.common.core.data.ScalpelTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "数据源")
public class GenDatasourceInfo implements Serializable {
    /**
     * 数据源表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "数据源表id")
    private Integer id;

    /**
     * 数据库类型:mysql,oracle,sqlsever
     */
    @ApiModelProperty(value = "数据库类型:mysql,oracle,sqlsever")
    private String type;

    /**
     * 数据库名
     */
    @ApiModelProperty(value = "数据库名")
    private String name;

    /**
     * 数据库host
     */
    @ApiModelProperty(value = "数据库host")
    private String host;

    /**
     * 数据库端口
     */
    @TableField(value = "port")
    @Scalpel(type = ScalpelTypeEnum.MOSAIC, before = 0, after = 0)
    @ApiModelProperty(value = "数据库端口")
    private String port;

    /**
     * 用户名
     */
    @Scalpel(type = ScalpelTypeEnum.MOSAIC, before = 0, after = 0)
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @Scalpel(type = ScalpelTypeEnum.MOSAIC, before = 0, after = 0)
    private String password;

    private static final long serialVersionUID = 1L;
}
