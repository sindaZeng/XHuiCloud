package com.xhuicloud.pay.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.data.tenant.XHuiCommonThreadLocalHolder;
import com.xhuicloud.common.zero.base.IDGenerate;
import com.xhuicloud.pay.dto.PayOrderDto;
import com.xhuicloud.pay.entity.PayOrderAll;
import com.xhuicloud.pay.mapper.PayOrderAllMapper;
import com.xhuicloud.pay.service.PayOrderAllService;
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
        payOrderAll.setTenantId(XHuiCommonThreadLocalHolder.getTenant());
        payOrderAll.setState(0);
        save(payOrderAll);
        return payOrderAll;
    }
}
