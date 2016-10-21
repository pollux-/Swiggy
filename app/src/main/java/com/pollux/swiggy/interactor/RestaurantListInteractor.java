package com.pollux.swiggy.interactor;

import com.pollux.swiggy.model.Restaurant;

import rx.Observable;

/**
 * Created by Sree Kumar
 * <p>
 * Copyright (C) 2016
 */
public interface RestaurantListInteractor {
    Observable<Restaurant> getRestaurantListInteractorList(final int pageNo);
}
