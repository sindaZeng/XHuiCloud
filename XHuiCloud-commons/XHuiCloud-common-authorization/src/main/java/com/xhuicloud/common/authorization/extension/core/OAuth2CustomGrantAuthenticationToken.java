package com.xhuicloud.common.authorization.extension.core;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OAuth2CustomGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    @Getter
    private final Set<String> scopes;

    /**
     * Sub-class constructor.
     *
     * @param authorizationGrantType the authorization grant type
     * @param clientPrincipal        the authenticated client principal
     * @param additionalParameters   the additional parameters
     * @param scopes                 the authenticated client scope
     */
    public OAuth2CustomGrantAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                   Authentication clientPrincipal,
                                                   Map<String, Object> additionalParameters,
                                                   Set<String> scopes) {
        super(authorizationGrantType, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
    }
}
