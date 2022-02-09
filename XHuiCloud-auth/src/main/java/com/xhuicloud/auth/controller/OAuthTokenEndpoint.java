///*
// * MIT License
// * Copyright <2021-2022>
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
// * of the Software, and to permit persons to whom the Software is furnished to do so,
// * subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in all
// * copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
// * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
// * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
// * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// * @Author: Sinda
// * @Email:  xhuicloud@163.com
// */
//
//package com.xhuicloud.auth.controller;
//
//import com.xhuicloud.common.core.utils.Response;
//import com.xhuicloud.common.security.component.XHuiWebResponseExceptionTranslator;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.provider.ClientRegistrationException;
//import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
//import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.*;
//import java.security.Principal;
//import java.util.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/oauth")
//@RequiredArgsConstructor
//public class OAuthTokenEndpoint {
//
//    private final TokenEndpoint tokenEndpoint;
//
//    /**
//     * 修改Oauth2定义的错误信息 为我们定义的错误信息
//     */
//    private WebResponseExceptionTranslator<OAuth2Exception> providerExceptionHandler = new XHuiWebResponseExceptionTranslator();
//
//    @RequestMapping(value = "/token", method=RequestMethod.POST)
//    public Response<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam
//            Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
//        ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = tokenEndpoint.postAccessToken(principal, parameters);
//        OAuth2AccessToken body = oAuth2AccessTokenResponseEntity.getBody();
//        return Response.success(body);
//    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<OAuth2Exception> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Exception {
//        if (log.isInfoEnabled()) {
//            log.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
//        }
//        return providerExceptionHandler.translate(e);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
//        if (log.isErrorEnabled()) {
//            log.error("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage(), e);
//        }
//        return providerExceptionHandler.translate(e);
//    }
//
//    @ExceptionHandler(ClientRegistrationException.class)
//    public ResponseEntity<OAuth2Exception> handleClientRegistrationException(Exception e) throws Exception {
//        if (log.isWarnEnabled()) {
//            log.warn("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
//        }
//        return providerExceptionHandler.translate(new BadClientCredentialsException());
//    }
//
//    @ExceptionHandler(OAuth2Exception.class)
//    public ResponseEntity<OAuth2Exception> handleException(OAuth2Exception e) throws Exception {
//        if (log.isWarnEnabled()) {
//            log.warn("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
//        }
//        return providerExceptionHandler.translate(e);
//    }
//
//}
