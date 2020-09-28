package com.xhuicloud.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value="商户渠道表")
public class PayChannel implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 应用id
     */
    @ApiModelProperty(value="应用id")
    private String appId;

    /**
     * 提供商模式商户id
     */
    @ApiModelProperty(value="提供商模式商户id")
    private String mchId;

    /**
     * 渠道商id
     */
    @ApiModelProperty(value="渠道商id")
    private String channelId;

    /**
     * 渠道商名称
     */
    @ApiModelProperty(value="渠道商名称")
    private String channelName;

    /**
     * 渠道商商户id
     */
    @ApiModelProperty(value="渠道商商户id")
    private String channelMchId;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;

    /**
     * 0: 禁用 1：启用
     */
    @ApiModelProperty(value="0: 禁用 1：启用")
    private Integer delFlag;

    /**
     * 租户id
     */
    @ApiModelProperty(value="租户id")
    private Integer tenantId;

    /**
     * json格式的配置
     */
    @ApiModelProperty(value="json格式的配置")
    private String config;

    private static final long serialVersionUID = 1L;
}
