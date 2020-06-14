package com.zsinda.fdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.base.IDGenerate;
import com.zsinda.fdp.dto.PayOrderDto;
import com.zsinda.fdp.entity.PayOrderAll;
import com.zsinda.fdp.mapper.PayOrderAllMapper;
import com.zsinda.fdp.service.PayOrderAllService;
import com.zsinda.fdp.tenant.FdpTenantHolder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PayOrderAllServiceImpl extends ServiceImpl<PayOrderAllMapper, PayOrderAll> implements PayOrderAllService {

    private final IDGenerate defaultSnowflakeIDGenerate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayOrderAll create(PayOrderDto payOrderDto) {
        PayOrderAll payOrderAll = new PayOrderAll();
        BeanUtil.copyProperties(payOrderDto, payOrderAll);
        payOrderAll.setOrderNo(String.valueOf(defaultSnowflakeIDGenerate.get()));
        payOrderAll.setTenantId(FdpTenantHolder.getTenant());
        payOrderAll.setState(0);
        save(payOrderAll);
        return payOrderAll;
    }
}
