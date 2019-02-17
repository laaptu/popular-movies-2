package com.laaptu.popmovies.network;

import com.laaptu.popmovies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl httpUrl = originalRequest.url();
        HttpUrl modifiedUrl = httpUrl.newBuilder()
            .addQueryParameter(APIConstants.QUERY_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
            .build();
        return chain.proceed(originalRequest.newBuilder().url(modifiedUrl).build());
    }
}


