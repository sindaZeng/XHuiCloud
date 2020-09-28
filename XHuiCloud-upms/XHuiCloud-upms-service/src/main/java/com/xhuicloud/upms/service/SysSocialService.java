package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysSocial;

public interface SysSocialService extends IService<SysSocial> {

    UserInfo getSysUser(String auth_code);
}
