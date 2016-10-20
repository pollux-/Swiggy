package com.pollux.swiggy.server;

import com.pollux.swiggy.model.Restaurant;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2016
 */
public interface SwiggyApi {
    @GET("bins/ngcc")
    Observable<Restaurant> getRestaurantList(@Query("page") int page);
}
