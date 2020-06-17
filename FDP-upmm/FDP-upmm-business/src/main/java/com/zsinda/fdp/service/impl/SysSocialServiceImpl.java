package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.entity.SysSocial;
import com.zsinda.fdp.handle.SocialHandle;
import com.zsinda.fdp.mapper.SysSocialMapper;
import com.zsinda.fdp.service.SysSocialService;
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
        return handle.get(inStrs[0]).handle(inStrs[1]);
    }
}
