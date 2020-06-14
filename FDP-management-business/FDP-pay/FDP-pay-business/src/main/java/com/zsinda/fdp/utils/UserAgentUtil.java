package com.zsinda.fdp.utils;

import com.zsinda.fdp.constant.CommonConstants;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: FDPlatform
 * @description: UserAgentUtil
 * @author: Sinda
 * @create: 2020-06-08 11:24
 */
@UtilityClass
public class UserAgentUtil {

    public Boolean isWeChat(HttpServletRequest request){
        String UA = request.getHeader(HttpHeaders.USER_AGENT);
        return UA.contains(CommonConstants.MICRO_MESSENGER);
    }
}
