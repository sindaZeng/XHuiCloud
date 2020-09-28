package com.xhuicloud.pay.handle;

import com.xhuicloud.pay.dto.PayOrderDto;

/**
 * @program: XHuiCloud
 * @description: PayService
 * @author: Sinda
 * @create: 2020-06-05 10:42
 */
public interface PayService {

    /**
     * 唤起支付渠道
     *
     * @return
     */
    Object pay(PayOrderDto payOrderDto);
}
