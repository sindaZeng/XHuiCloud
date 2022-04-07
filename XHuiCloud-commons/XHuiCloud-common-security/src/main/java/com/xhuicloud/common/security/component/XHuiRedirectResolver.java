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

package com.xhuicloud.common.security.component;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.xhuicloud.common.core.utils.WebUtils;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.endpoint.RedirectResolver;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

public class XHuiRedirectResolver implements RedirectResolver {

    private Collection<String> redirectGrantTypes = Arrays.asList("implicit", "authorization_code");

    private boolean matchSubdomains = false;

    private boolean matchPorts = true;

    /**
     * Flag to indicate that requested URIs will match if they are a subdomain of the registered value.
     *
     * @param matchSubdomains the flag value to set (default true)
     */
    public void setMatchSubdomains(boolean matchSubdomains) {
        this.matchSubdomains = matchSubdomains;
    }

    /**
     * Flag that enables/disables port matching between the requested redirect URI and the registered redirect URI(s).
     *
     * @param matchPorts true to enable port matching, false to disable (defaults to true)
     */
    public void setMatchPorts(boolean matchPorts) {
        this.matchPorts = matchPorts;
    }

    /**
     * Grant types that are permitted to have a redirect uri.
     *
     * @param redirectGrantTypes the redirect grant types to set
     */
    public void setRedirectGrantTypes(Collection<String> redirectGrantTypes) {
        this.redirectGrantTypes = new HashSet<String>(redirectGrantTypes);
    }

    @SneakyThrows
    public String resolveRedirect(String requestedRedirect, ClientDetails client) throws OAuth2Exception {

        Set<String> authorizedGrantTypes = client.getAuthorizedGrantTypes();
        if (authorizedGrantTypes.isEmpty()) {
            WebUtils.getResponse().sendRedirect(HttpUtil.encodeParams(String.format("/auth/notmatch?error=%s", "客户端必须具有至少一种授权授予类型"), CharsetUtil.CHARSET_UTF_8));
            throw new InvalidGrantException("A client must have at least one authorized grant type.");
        }
        if (!containsRedirectGrantType(authorizedGrantTypes)) {
            WebUtils.getResponse().sendRedirect(HttpUtil.encodeParams(String.format("/auth/notmatch?error=%s", "当前客户端不允许使用"), CharsetUtil.CHARSET_UTF_8));
            throw new InvalidGrantException(
                    "A redirect_uri can only be used by implicit or authorization_code grant types.");
        }

        Set<String> registeredRedirectUris = client.getRegisteredRedirectUri();
        if (registeredRedirectUris == null || registeredRedirectUris.isEmpty()) {
            WebUtils.getResponse().sendRedirect(HttpUtil.encodeParams(String.format("/auth/notmatch?error=%s", "当前客户端没有设置回调域"), CharsetUtil.CHARSET_UTF_8));
            throw new InvalidRequestException("At least one redirect_uri must be registered with the client.");
        }
        return obtainMatchingRedirect(registeredRedirectUris, requestedRedirect);
    }

    /**
     * @param grantTypes some grant types
     * @return true if the supplied grant types includes one or more of the redirect types
     */
    private boolean containsRedirectGrantType(Set<String> grantTypes) {
        for (String type : grantTypes) {
            if (redirectGrantTypes.contains(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether the requested redirect URI "matches" the specified redirect URI. For a URL, this implementation tests if
     * the user requested redirect starts with the registered redirect, so it would have the same host and root path if
     * it is an HTTP URL. The port, userinfo, query params also matched. Request redirect uri path can include
     * additional parameters which are ignored for the match
     * <p>
     * For other (non-URL) cases, such as for some implicit clients, the redirect_uri must be an exact match.
     *
     * @param requestedRedirect The requested redirect URI.
     * @param redirectUri The registered redirect URI.
     * @return Whether the requested redirect URI "matches" the specified redirect URI.
     */
    protected boolean redirectMatches(String requestedRedirect, String redirectUri) {
        UriComponents requestedRedirectUri = UriComponentsBuilder.fromUriString(requestedRedirect).build();
        UriComponents registeredRedirectUri = UriComponentsBuilder.fromUriString(redirectUri).build();

        boolean schemeMatch = isEqual(registeredRedirectUri.getScheme(), requestedRedirectUri.getScheme());
        boolean userInfoMatch = isEqual(registeredRedirectUri.getUserInfo(), requestedRedirectUri.getUserInfo());
        boolean hostMatch = hostMatches(registeredRedirectUri.getHost(), requestedRedirectUri.getHost());
        boolean portMatch = matchPorts ? registeredRedirectUri.getPort() == requestedRedirectUri.getPort() : true;
        boolean pathMatch = isEqual(registeredRedirectUri.getPath(),
                StringUtils.cleanPath(requestedRedirectUri.getPath()));
        boolean queryParamMatch = matchQueryParams(registeredRedirectUri.getQueryParams(),
                requestedRedirectUri.getQueryParams());

        return schemeMatch && userInfoMatch && hostMatch && portMatch && pathMatch && queryParamMatch;
    }


    /**
     * Checks whether the registered redirect uri query params key and values contains match the requested set
     *
     * The requested redirect uri query params are allowed to contain additional params which will be retained
     *
     * @param registeredRedirectUriQueryParams
     * @param requestedRedirectUriQueryParams
     * @return whether the params match
     */
    private boolean matchQueryParams(MultiValueMap<String, String> registeredRedirectUriQueryParams,
                                     MultiValueMap<String, String> requestedRedirectUriQueryParams) {


        Iterator<String> iter = registeredRedirectUriQueryParams.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            List<String> registeredRedirectUriQueryParamsValues = registeredRedirectUriQueryParams.get(key);
            List<String> requestedRedirectUriQueryParamsValues = requestedRedirectUriQueryParams.get(key);

            if (!registeredRedirectUriQueryParamsValues.equals(requestedRedirectUriQueryParamsValues)) {
                return false;
            }
        }

        return true;
    }



    /**
     * Compares two strings but treats empty string or null equal
     *
     * @param str1
     * @param str2
     * @return true if strings are equal, false otherwise
     */
    private boolean isEqual(String str1, String str2) {
        if (StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)) {
            return true;
        } else if (!StringUtils.isEmpty(str1)) {
            return str1.equals(str2);
        } else {
            return false;
        }
    }

    /**
     * Check if host matches the registered value.
     *
     * @param registered the registered host
     * @param requested the requested host
     * @return true if they match
     */
    protected boolean hostMatches(String registered, String requested) {
        if (matchSubdomains) {
            return registered.equals(requested) || requested.endsWith("." + registered);
        }
        return registered.equals(requested);
    }

    /**
     * Attempt to match one of the registered URIs to the that of the requested one.
     *
     * @param redirectUris the set of the registered URIs to try and find a match. This cannot be null or empty.
     * @param requestedRedirect the URI used as part of the request
     * @return redirect uri
     * @throws RedirectMismatchException if no match was found
     */
    private String obtainMatchingRedirect(Set<String> redirectUris, String requestedRedirect) throws IOException {

        if (requestedRedirect == null) {
            WebUtils.getResponse().sendRedirect(HttpUtil.encodeParams(String.format("/auth/notmatch?error=%s", "请求请携带回调域"), CharsetUtil.CHARSET_UTF_8));
            throw new InvalidRequestException("redirect_uri can not be null.");
        }

        for (String redirectUri : redirectUris) {
            if (requestedRedirect != null && redirectMatches(requestedRedirect, redirectUri)) {
                // Initialize with the registered redirect-uri
                UriComponentsBuilder redirectUriBuilder = UriComponentsBuilder.fromUriString(redirectUri);

                UriComponents requestedRedirectUri = UriComponentsBuilder.fromUriString(requestedRedirect).build();

                if (this.matchSubdomains) {
                    redirectUriBuilder.host(requestedRedirectUri.getHost());
                }
                if (!this.matchPorts) {
                    redirectUriBuilder.port(requestedRedirectUri.getPort());
                }
                redirectUriBuilder.replaceQuery(requestedRedirectUri.getQuery());		// retain additional params (if any)
                redirectUriBuilder.fragment(null);
                return redirectUriBuilder.build().toUriString();
            }
        }
        WebUtils.getResponse().sendRedirect(HttpUtil.encodeParams(String.format("/auth/notmatch?error=%s", "回调域错误"), CharsetUtil.CHARSET_UTF_8));
        throw new RedirectMismatchException("Invalid redirect: " + requestedRedirect
                + " does not match one of the registered values.");
    }
}
