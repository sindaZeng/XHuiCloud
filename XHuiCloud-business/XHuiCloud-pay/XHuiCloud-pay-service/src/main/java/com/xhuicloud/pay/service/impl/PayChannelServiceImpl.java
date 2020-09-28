package com.xhuicloud.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.pay.entity.PayChannel;
import com.xhuicloud.pay.mapper.PayChannelMapper;
import com.xhuicloud.pay.service.PayChannelService;
import org.springframework.stereotype.Service;

@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements PayChannelService {

}
