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

package com.xhuicloud.auth.service;

import com.xhuicloud.common.core.constant.SecurityConstants;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XHuiAuthCodeServicesImpl extends RandomValueAuthorizationCodeServices {

    private final RedisConnectionFactory connectionFactory;

    @Setter
    private String prefix = SecurityConstants.XHUI_PREFIX + SecurityConstants.OAUTH_CODE_PREFIX;

    @Setter
    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    /**
     * 保存 code 和 认证信息
     * @param code 授权码模式： code
     * @param authentication 认证信息
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        @Cleanup
        RedisConnection connection = connectionFactory.getConnection();
        connection.set(serializationStrategy.serialize(prefix + code), serializationStrategy.serialize(authentication));
    }

    /**
     * 删除code 并返回认证信息
     * @param code 授权码模式： code
     * @return
     */
    @Override
    protected OAuth2Authentication remove(String code) {
        @Cleanup
        RedisConnection connection = connectionFactory.getConnection();

        byte[] key = serializationStrategy.serialize(prefix + code);
        byte[] value = connection.get(key);

        if (value == null) {
            return null;
        }

        OAuth2Authentication oAuth2Authentication = serializationStrategy.deserialize(value,
                OAuth2Authentication.class);

        connection.del(key);
        return oAuth2Authentication;
    }

}
