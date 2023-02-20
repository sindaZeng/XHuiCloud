package com.xhuicloud.ai.socket;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.ttl.XHuiCommonThreadLocalHolder;
import lombok.AllArgsConstructor;
import net.dreamlu.iot.mqtt.core.server.auth.IMqttServerAuthHandler;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.websocket.common.WsSessionContext;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/18
 */
@Service
@AllArgsConstructor
public class MqttServerAuth implements IMqttServerAuthHandler {

    private final RedisTemplate<String, Object> redisTemplate;

//    private final OpaqueTokenIntrospector introspector;

    @Override
    public boolean authenticate(ChannelContext context, String uniqueId, String clientId, String userName, String password) {
        WsSessionContext wsSessionContext = (WsSessionContext) context.get();
        HttpRequest request = wsSessionContext.getHandshakeRequest();
        String sessionId = request.getParam(CommonConstants.SESSION_ID);
        Long tenantId = request.getLong(CommonConstants.TENANT_ID);
        if (StrUtil.isBlank(sessionId) || tenantId == null) {
            return false;
        }
        XHuiCommonThreadLocalHolder.setTenant(tenantId);
        // 远程调用查询用户 2023-2-20弃用 不是最佳方案
//        OAuth2AuthenticatedPrincipal principal = introspector.introspect(access_token);
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(convert(principal, access_token));
//        SecurityContextHolder.setContext(securityContext);

        Object userId = redisTemplate.opsForValue().get(SecurityHolder.buildCacheKey(CommonConstants.SESSION_ID, sessionId));
        if (userId == null) {
            return false;
        }
        context.tioConfig.users.bind(userId.toString(), context);
        context.tioConfig.tokens.bind(tenantId.toString(), context);
        return true;
    }

//    private AbstractAuthenticationToken convert(OAuth2AuthenticatedPrincipal principal, String token) {
//        Instant iat = principal.getAttribute(OAuth2TokenIntrospectionClaimNames.IAT);
//        Instant exp = principal.getAttribute(OAuth2TokenIntrospectionClaimNames.EXP);
//        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, token, iat, exp);
//        return new BearerTokenAuthentication(principal, accessToken, principal.getAuthorities());
//    }
}

