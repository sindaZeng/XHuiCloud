package com.zsinda.fdp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.entity.SysSocial;

public interface SysSocialService extends IService<SysSocial> {

    UserInfo getSysUser(String auth_code);
}
