package com.pollux.swiggy.interactor;

import com.pollux.swiggy.model.Restaurant;
import com.pollux.swiggy.server.SwiggyService;
import com.pollux.swiggy.utils.RxUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2016
 */

/**
 * Get Restaurant list and their chain
 */
public class RestaurantListInteractorImpl implements RestaurantListInteractor {
    @Override
    public Observable<Restaurant> getRestaurantListInteractorList(int pageNo) {
        return SwiggyService.getSwiggyService().getRestaurantList(pageNo).map(new Func1<Restaurant, Restaurant>() {
            @Override
            public Restaurant call(Restaurant restaurant) {
                if (restaurant != null) {
                    final List<Restaurant.Restaurants> restaurants = restaurant.getRestaurants();
                    if (restaurants != null && restaurants.size() > 0) {
                        for (Restaurant.Restaurants rest : restaurants) {
                            if (rest != null && rest.getCuisine() != null) {
                                rest.cuisineFormatted ="";
                                final List<String> cuisines = rest.getCuisine();
                                for (String cuisine : cuisines) {
                                    rest.cuisineFormatted += cuisine + ",";
                                }
                                rest.cuisineFormatted = rest.cuisineFormatted.substring(0,rest.cuisineFormatted.length()-1);
                            }
                        }
                    }

                }

                return restaurant;
            }
        })
                .compose(RxUtils.<Restaurant>applySchedulers());
    }
}
