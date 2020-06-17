package com.zsinda.fdp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: FDPlatform
 * @description: PayOrderDto
 * @author: Sinda
 * @create: 2020-06-09 10:17
 */
@Data
public class PayOrderDto {

    /**
     * 平台订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;

    /**
     * 商品id,注:转账付款没有ID
     */
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    /**
     * 商品标题
     */
    @ApiModelProperty(value = "商品标题")
    private String goodsTitle;

    /**
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注")
    private String remark;

    /**
     * 用户付款中途退出返回商户网站的地址
     */
    @ApiModelProperty(value = "用户付款中途退出返回商户网站的地址")
    private String quitUrl;

    /**
     * 第三方渠道名称
     */
    @ApiModelProperty(value = "第三方渠道名称")
    private String channelId;

    /**
     * 第三方渠道用户openId
     */
    @ApiModelProperty(value = "第三方渠道用户openId")
    private String openId;

    /**
     * 预留字段:第三方授权码
     */
    @ApiModelProperty(value = "第三方授权码")
    private String code;

}
