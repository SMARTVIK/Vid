package com.hunter.videostatus.gifandvideos;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://andyzoneinfotech.com/video_status_api/";
    public static final String BASE_URL_GIF = "http://andyzoneinfotech.com/";
    private static Retrofit retrofit = null;
    static boolean videoBase = true;


    public static Retrofit getClientVideo() {
        if (!(retrofit == null || retrofit.baseUrl().toString().equalsIgnoreCase(BASE_URL))) {
            videoBase = false;
        }
        if (retrofit == null || !videoBase) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).addInterceptor(new Interceptor() {
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    request.newBuilder().addHeader("Cache-Control", "no-cache");
                    return chain.proceed(request);
                }
            }).build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getClientGif() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL_GIF).client(new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).addInterceptor(new Interceptor() {
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    request.newBuilder().addHeader("Cache-Control", "no-cache");
                    return chain.proceed(request);
                }
            }).build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}