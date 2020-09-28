package com.xhuicloud.common.security.component;

import com.xhuicloud.common.core.constant.AuthorizationConstants;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.security.service.XHuiUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: XHuiCloud
 * @description: token 增强
 * @author: Sinda
 * @create: 2019-12-27 00:11
 **/
public class XHuiTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (AuthorizationConstants.CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
            return accessToken;
        }
        final Map<String, Object> additionalInfo = new HashMap<>(8);
        XHuiUser XHuiUser = (XHuiUser) authentication.getUserAuthentication().getPrincipal();
        additionalInfo.put(SecurityConstants.USER_INFO, XHuiUser);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
