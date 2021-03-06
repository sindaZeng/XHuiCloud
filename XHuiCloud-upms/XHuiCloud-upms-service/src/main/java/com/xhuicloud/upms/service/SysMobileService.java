package com.xhuicloud.upms.service;


import com.xhuicloud.common.core.utils.R;

/**
 * @program: XHuiCloud
 * @description: MobileService
 * @author: Sinda
 * @create: 2019-12-26 23:37
 **/
public interface SysMobileService {
    /**
     * 发送手机验证码
     *
     * @param mobile mobile
     * @return code
     */
    R<Boolean> sendSmsCode(String mobile);
}
