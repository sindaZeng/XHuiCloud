package com.zsinda.fdp.enums.pay;

import com.zsinda.fdp.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: FDPlatform
 * @description: PayTypeEnum
 * @author: Sinda
 * @create: 2020-06-05 18:16
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    /**
     * 支付宝wap支付
     */
    ALIPAY_WAP("ALIPAY_WAP", "支付宝H5支付"),

    /**
     * 微信H5支付
     */
    WEIXIN_WAP("WEIXIN_WAP", "微信H5支付"),

    /**
     * 微信公众号支付
     */
    WEIXIN_MP("WEIXIN_MP", "微信公众号支付");

    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String description;

    /**
     * 通过浏览器ua 判断所属渠道
     *
     * @param ua 浏览器类型
     * @return
     */
    public static Enum getChannel(String ua) {
        if (ua.contains(CommonConstants.ALIPAY)) {
            return PayTypeEnum.ALIPAY_WAP;
        } else if (ua.contains(CommonConstants.MICRO_MESSENGER)) {
            return PayTypeEnum.WEIXIN_MP;
        } else {
            return PayTypeEnum.WEIXIN_WAP;
        }
    }
}
