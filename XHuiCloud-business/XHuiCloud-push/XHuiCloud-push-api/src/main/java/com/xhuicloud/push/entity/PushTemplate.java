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

package com.xhuicloud.push.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value="推送模板")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushTemplate extends Model<PushTemplate> {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 租户id
     */
    @ApiModelProperty(value="租户id")
    private Long tenantId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    /**
     * 0: 否 1：是
     */
    @ApiModelProperty(value="0: 否 1：是")
    private Boolean isDel;

    /**
     * 创建者id
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者id", hidden = true)
    private Long createId;

    /**
     * 更新者id
     */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新者id", hidden = true)
    private Long updateId;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 0:正常 1:停用
     */
    @ApiModelProperty(value="0:正常 1:停用")
    private Boolean status;

    /**
     * 参数对应的key与value
     */
    @ApiModelProperty(value="参数对应的key与value")
    private String kv;

    /**
     * 组id
     */
    @ApiModelProperty(value="组id")
    private Long groupId;

    /**
     * 渠道
     */
    @ApiModelProperty(value="渠道")
    private String channel;

    /**
     * 渠道模板id
     */
    @ApiModelProperty(value="渠道模板id")
    private String channelId;

}