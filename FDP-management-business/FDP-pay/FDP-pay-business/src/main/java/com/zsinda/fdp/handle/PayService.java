package com.zsinda.fdp.handle;

import com.zsinda.fdp.dto.PayOrderDto;

/**
 * @program: FDPlatform
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
