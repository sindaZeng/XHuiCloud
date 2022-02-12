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

package com.xhuicloud.test.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;

/**
* @program: Test
* @description: 商户表
* @author: Sinda
* @create: 2022-02-11 17:44:19
*/
@Data
@TableName("sys_merchant")
@ApiModel(value = "商户表")
public class SysMerchant implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "$column.columnName", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @ApiModelProperty(value="商户编号（商户-yl000000， 门店-yl000000-0001）")
    private String merchantCode;

    @ApiModelProperty(value="商户名称")
    private String name;

    @ApiModelProperty(value="简称")
    private String shortName;

    @ApiModelProperty(value="类型（字典  S 系统运营  A渠道代理  T商户 D门店）")
    private String systype;

    @ApiModelProperty(value="门店所属区域")
    private String areaCode;

    @ApiModelProperty(value="总部商户ID")
    private String parentCode;

    @ApiModelProperty(value="总部商户名称")
    private String parentName;

    @ApiModelProperty(value="所属行业ID")
    private Integer industryId;

    @ApiModelProperty(value="是否连锁  1是  0否")
    private Integer isChain;

    @ApiModelProperty(value="联系人姓名")
    private String contactName;

    @ApiModelProperty(value="联系人电话")
    private String contactTel;

    @ApiModelProperty(value="")
    private String logoUrl;

    @ApiModelProperty(value="备注")
    private String remarks;

    @ApiModelProperty(value="国家")
    private String countryName;

    @ApiModelProperty(value="省份id")
    private Long provinceId;

    @ApiModelProperty(value="城市id")
    private Long cityId;

    @ApiModelProperty(value="县id")
    private Long countyId;

    @ApiModelProperty(value="区id")
    private Long areaId;

    @ApiModelProperty(value="省份")
    private String provinceName;

    @ApiModelProperty(value="城市")
    private String cityName;

    @ApiModelProperty(value="县")
    private String countyName;

    @ApiModelProperty(value="区")
    private String areaName;

    @ApiModelProperty(value="详细地址")
    private String address;

    @ApiModelProperty(value="秘钥")
    private String key;

    @ApiModelProperty(value="创建人用户ID，对应t_user表ID")
    private String createBy;

    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value="更新人")
    private String updateBy;

    @ApiModelProperty(value="更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value="数据状态（字典1=正常，2=冻结，3=删除）")
    private Integer status;

    @ApiModelProperty(value="售后专员")
    private String afterSalesSpecialis;

    @ApiModelProperty(value="收银")
    private String cashRegister;


}
