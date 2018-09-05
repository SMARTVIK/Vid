package com.hunter.videostatus.vidstatus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GifStatus {

    public static final String GIPHY_APY_KEY = "P5UueeDxVfolbDunOTzusLNDH5gnIB5O";
    public static final String BASE_URL = "http://www.andyzoneinfotech.com/";
    public static final String BASE_URLG = "http://api.giphy.com/v1/gifs/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofitG = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getClientG() {
        if (retrofitG == null) {
            retrofitG = new Retrofit.Builder().baseUrl(BASE_URLG).client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitG;
    }
}
