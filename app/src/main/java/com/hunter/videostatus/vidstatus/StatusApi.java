package com.hunter.videostatus.vidstatus;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusApi {
    private Api api;
    private Api2Domain api2Domain ;
    private Api2 api2;
    private Gson gson = new Gson();

    public  Api getStatusApi() {
        if (api == null) {
            api = new Retrofit.Builder().baseUrl("http://vidstatus.in/vidstatus/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(Api.class);
        }
        return api;
    }


    public  Api getGifApi() {
        if (api == null) {
            api = new Retrofit.Builder().baseUrl("http://andyzoneinfotech.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(Api.class);
        }
        return api;
    }
}
