package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.dto.PayOrderDto;
import com.zsinda.fdp.entity.PayOrderAll;

public interface PayOrderAllService extends IService<PayOrderAll> {

    /**
     * 生成订单
     *
     * @param payOrderDto
     */
    PayOrderAll create(PayOrderDto payOrderDto);

}
