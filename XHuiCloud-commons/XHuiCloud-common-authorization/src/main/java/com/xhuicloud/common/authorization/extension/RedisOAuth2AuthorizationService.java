/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.authorization.extension;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import com.xhuicloud.common.core.constant.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.Assert;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 扩展token存储
 */
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final static Long TIMEOUT = 10L;

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        String userId = authorization.getAttribute(CommonConstants.USER_ID);
        if (StrUtil.isNotBlank(userId)) {
            redisTemplate.opsForValue().set(SecurityHolder.buildCacheKey(OAuth2ParameterNames.USERNAME, userId), authorization, TIMEOUT,
                    TimeUnit.MINUTES);
        }

        // This state occurs with the authorization_code grant flow during the user consent step OR
        // when the code is returned in the authorization response but the access token request is not yet initiated.
        String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (Objects.nonNull(state)) {
            redisTemplate.opsForValue().set(SecurityHolder.buildCacheKey(OAuth2ParameterNames.STATE, state), authorization, TIMEOUT,
                    TimeUnit.MINUTES);
        }

        OAuth2Authorization.Token<OAuth2AuthorizationCode> code = authorization
                .getToken(OAuth2AuthorizationCode.class);
        if (Objects.nonNull(code)) {
            OAuth2AuthorizationCode authorizationCodeToken = code.getToken();
            long timeout = ChronoUnit.MINUTES.between(authorizationCodeToken.getIssuedAt(),
                    authorizationCodeToken.getExpiresAt());
            redisTemplate.opsForValue().set(SecurityHolder.buildCacheKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
                    authorization, timeout, TimeUnit.MINUTES);
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refresh_token = authorization.getRefreshToken();
        if (Objects.nonNull(refresh_token)) {
            OAuth2RefreshToken refreshToken = refresh_token.getToken();
            long timeout = ChronoUnit.SECONDS.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
            redisTemplate.opsForValue().set(SecurityHolder.buildCacheKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()),
                    authorization, timeout, TimeUnit.SECONDS);
        }

        OAuth2Authorization.Token<OAuth2AccessToken> access_token = authorization.getAccessToken();
        if (Objects.nonNull(access_token)) {
            OAuth2AccessToken accessToken = access_token.getToken();
            long timeout = ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            redisTemplate.opsForValue().set(SecurityHolder.buildCacheKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()),
                    authorization, timeout, TimeUnit.SECONDS);
        }

    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        List<String> keys = new ArrayList<>();

        String userId = authorization.getAttribute(CommonConstants.USER_ID);
        if (StrUtil.isNotBlank(userId)) {
            keys.add(SecurityHolder.buildCacheKey(OAuth2ParameterNames.USERNAME, userId));
        }

        String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (Objects.nonNull(state)) {
            keys.add(SecurityHolder.buildCacheKey(OAuth2ParameterNames.STATE, state));
        }

        OAuth2Authorization.Token<OAuth2AuthorizationCode> code = authorization
                .getToken(OAuth2AuthorizationCode.class);
        if (Objects.nonNull(code)) {
            OAuth2AuthorizationCode authorizationCodeToken = code.getToken();
            keys.add(SecurityHolder.buildCacheKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()));
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refresh_token = authorization.getRefreshToken();
        if (Objects.nonNull(refresh_token)) {
            OAuth2RefreshToken refreshToken = refresh_token.getToken();
            keys.add(SecurityHolder.buildCacheKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()));
        }

        OAuth2Authorization.Token<OAuth2AccessToken> access_token = authorization.getAccessToken();
        if (Objects.nonNull(access_token)) {
            OAuth2AccessToken accessToken = access_token.getToken();
            keys.add(SecurityHolder.buildCacheKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()));
        }
        redisTemplate.delete(keys);
    }

    @Override
    public OAuth2Authorization findById(String principalName) {
        Assert.hasText(principalName, "principalName cannot be empty");
        return (OAuth2Authorization) redisTemplate.opsForValue().get(SecurityHolder.buildCacheKey(OAuth2ParameterNames.USERNAME, principalName));
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        tokenType = ObjectUtil.defaultIfNull(tokenType, new OAuth2TokenType(OAuth2ParameterNames.ACCESS_TOKEN));
        return (OAuth2Authorization) redisTemplate.opsForValue().get(SecurityHolder.buildCacheKey(tokenType.getValue(), token));
    }



}
