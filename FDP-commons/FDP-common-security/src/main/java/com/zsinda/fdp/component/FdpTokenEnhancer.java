package com.zsinda.fdp.component;

import com.zsinda.fdp.constant.AuthorizationConstants;
import com.zsinda.fdp.service.impl.FdpUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: FDPlatform
 * @description: token 增强
 * @author: Sinda
 * @create: 2019-12-27 00:11
 **/
public class FdpTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (AuthorizationConstants.CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
            return accessToken;
        }
        final Map<String, Object> additionalInfo = new HashMap<>(8);
        FdpUser fdpUser = (FdpUser) authentication.getUserAuthentication().getPrincipal();
        additionalInfo.put(AuthorizationConstants.USER_ID, fdpUser.getId());
        additionalInfo.put(AuthorizationConstants.USER_NAME, fdpUser.getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
