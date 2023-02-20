package com.xhuicloud.ai.chatgpt.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
public class AuthenticationInterceptor implements Interceptor {

    private final String token;

    public AuthenticationInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(request);
    }
}