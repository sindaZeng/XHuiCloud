package com.xhuicloud.common.security.component;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.security.exception.XHuiOAuth2Exception;
import com.xhuicloud.common.security.service.XHuiUser;
import com.xhuicloud.common.security.utils.SecurityMessageUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/25 5:23 下午
 */
public class XHuiUserAuthenticationConverter implements UserAuthenticationConverter {


    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            map = MapUtil.get(map, SecurityConstants.USER_INFO, Map.class);
            checkTenant(map);
            String username = MapUtil.getStr(map, CommonConstants.USER_USERNAME);
            Integer id = MapUtil.getInt(map, CommonConstants.USER_ID);
            Integer tenantId = MapUtil.getInt(map, CommonConstants.USER_TENANT_ID);
            String tenantName = MapUtil.getStr(map, CommonConstants.USER_TENANT_NAME);
            String phone = MapUtil.getStr(map, CommonConstants.USER_PHONE);

            XHuiUser user = new XHuiUser(id, phone, tenantId, tenantName, username,
                    "N_A", true, true, true, true,
                    authorities);
            return new UsernamePasswordAuthenticationToken(user, "N_A", authorities);
        }
        return null;
    }

    private void checkTenant(Map<String,?> map) {
        String headerValue = getTenantId();
        Integer userValue = MapUtil.getInt(map, CommonConstants.USER_TENANT_ID);
        if (StrUtil.isNotBlank(headerValue) && !userValue.toString().equals(headerValue)) {
            throw new XHuiOAuth2Exception(SecurityMessageUtil.getAccessor().getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badTenantId", new Object[] { headerValue },
                    "Bad tenant ID"));
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        return AuthorityUtils.NO_AUTHORITIES;
    }

    private Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).filter(
                requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
    }

    private String getTenantId() {
        return getCurrentHttpRequest()
                .map(httpServletRequest -> httpServletRequest.getHeader(CommonConstants.TENANT_ID)).orElse(null);
    }
}
