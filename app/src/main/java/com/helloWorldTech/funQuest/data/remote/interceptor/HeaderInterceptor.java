package com.helloWorldTech.funQuest.data.remote.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                //.addHeader("api_key", Config.API_KEY)
                //.removeHeader("User-Agent")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}