package com.pollux.swiggy.server;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2016
 */
public class SwiggyService {
    private static final int TIME_OUT_MINUTE = 5;
    private static SwiggyApi sSwiggyApi;

    /**
     * Create Swiggy service
     *
     * @return
     */
    public static SwiggyApi getSwiggyService() {

        if (sSwiggyApi == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(TIME_OUT_MINUTE, TimeUnit.MINUTES);
            builder.readTimeout(TIME_OUT_MINUTE, TimeUnit.MINUTES);
            builder.writeTimeout(TIME_OUT_MINUTE, TimeUnit.MINUTES);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.myjson.com/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();
            sSwiggyApi = retrofit.create(SwiggyApi.class);
        }
        return sSwiggyApi;

    }
}
