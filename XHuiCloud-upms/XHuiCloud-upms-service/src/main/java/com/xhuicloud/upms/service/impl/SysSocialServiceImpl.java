package com.xhuicloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.handle.SocialHandle;
import com.xhuicloud.upms.mapper.SysSocialMapper;
import com.xhuicloud.upms.service.SysSocialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@AllArgsConstructor
public class SysSocialServiceImpl extends ServiceImpl<SysSocialMapper, SysSocial> implements SysSocialService {

    private final Map<String, SocialHandle> handle;

    @Override
    public UserInfo getSysUser(String auth_code) {
        String[] inStrs = auth_code.split(StringPool.AT);
        return handle.get(inStrs[0].toUpperCase()).handle(inStrs[1]);
    }
}
