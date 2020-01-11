package com.zsinda.fdp.service;

import com.zsinda.fdp.utils.R;

/**
 * @program: FDPlatform
 * @description: MobileService
 * @author: Sinda
 * @create: 2019-12-26 23:37
 **/
public interface MobileService {
    /**
     * 发送手机验证码
     *
     * @param mobile mobile
     * @return code
     */
    R<Boolean> sendSmsCode(String mobile);
}
