package com.xhuicloud.common.security.component;

import com.xhuicloud.common.security.service.XHuiUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class XHuiResourceServerTokenServices implements ResourceServerTokenServices {

    private final TokenStore tokenStore;

    private final UserDetailsService userDetailsService;

    @Override
    public OAuth2Authentication loadAuthentication(String s) throws AuthenticationException, InvalidTokenException {
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(s);
        if (oAuth2Authentication == null) {
            return null;
        }

        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        if (!(oAuth2Authentication.getPrincipal() instanceof XHuiUser)) {
            return oAuth2Authentication;
        }

        XHuiUser xHuiUser = (XHuiUser) oAuth2Authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(xHuiUser.getUsername());
        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(userDetails, "N/A",
                userDetails.getAuthorities());
        OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
        authentication.setAuthenticated(true);
        return authentication;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        return tokenStore.readAccessToken(s);
    }
}
