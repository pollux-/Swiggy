package com.pollux.swiggy.view;

import android.support.annotation.NonNull;

import com.pollux.swiggy.model.Restaurant;

/**
 * Created by Sree Kumar
 * <p>
 * Copyright (C) 2016
 */
public interface RestaurantView extends AbsView {
    /**
     * Callback to Activity to inform completion of fetching
     * Restaurant.
     *
     * @param restaurants
     */
    void onDownloadRestaurantDetails(@NonNull Restaurant restaurants);
}
