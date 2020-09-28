package com.xhuicloud.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.pay.dto.PayOrderDto;
import com.xhuicloud.pay.entity.PayOrderAll;

public interface PayOrderAllService extends IService<PayOrderAll> {

    /**
     * 生成订单
     *
     * @param payOrderDto
     */
    PayOrderAll create(PayOrderDto payOrderDto);

}
