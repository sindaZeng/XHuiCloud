package com.xhuicloud.common.authorization.resource.config;

import com.xhuicloud.common.authorization.resource.userdetails.XHuiUser;
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

/**
 * 自定义token 转换器
 */
@AllArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final XHuiUserDetailsService xHuiUserDetailsService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, jwt.getTokenValue(),
                jwt.getIssuedAt(), jwt.getExpiresAt());
        String username = jwt.getClaimAsString("sub");
        XHuiUser userDetails = (XHuiUser) xHuiUserDetailsService.getUserDetails(username);
        return new BearerTokenAuthentication(userDetails, accessToken, userDetails.getAuthorities());
    }

}
