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

package com.xhuicloud.common.core.enums.pay;

import com.xhuicloud.common.core.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: XHuiCloud
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
