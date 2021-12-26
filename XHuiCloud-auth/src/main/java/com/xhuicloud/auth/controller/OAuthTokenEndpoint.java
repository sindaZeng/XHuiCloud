package com.xhuicloud.auth.controller;

import com.xhuicloud.common.core.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
public class OAuthTokenEndpoint {

    private final TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/token", method=RequestMethod.POST)
    public Response<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam
            Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = tokenEndpoint.postAccessToken(principal, parameters);
        OAuth2AccessToken body = oAuth2AccessTokenResponseEntity.getBody();
        return Response.success(body);
    }

}
