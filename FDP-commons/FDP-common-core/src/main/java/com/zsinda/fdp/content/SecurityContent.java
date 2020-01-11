package com.zsinda.fdp.content;

/**
 * @program: FDPlatform
 * @description: Security 公共内容
 * @author: Sinda
 * @create: 2020-01-01 22:58
 */
public interface SecurityContent {

    /**
     * 角色前缀
     */
    String ROLE_PREFIX ="ROLE_";

    /**
     * 是来自内部服务调用
     */
    String IS_COMMING_INNER_YES ="Y";

    /**
     * 不是来自内部服务调用
     */
    String IS_COMMING_INNER_NO ="N";

    /**
     * 标志
     */
    String FROM = "from";


    /**
     * 用户锁定
     */
    String USER_IS_LOCK = "1";

}
