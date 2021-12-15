package com.xhuicloud.common.core.constant;

/**
 * @program: XHuiCloud
 * @description: ThirdLoginUrlConstants
 * @author: Sinda
 * @create: 2020-06-17 17:25
 */
public interface ThirdLoginUrlConstants {

    /**
     * QQ accessToken url
     */
    String getTokenUrl = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    /**
     * QQ openId url
     */
    String getOpenIdUrl = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取QQ用户信息
     */
    String getQqUserInfoUrl = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";

    /**
     * 获取小程序/公众号 token
     */
    String MINI_WECHAT_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

}
