package com.pollux.swiggy.interactor;

import com.pollux.swiggy.model.Restaurant;
import com.pollux.swiggy.server.SwiggyService;
import com.pollux.swiggy.utils.RxUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Sree Kumar
 * <p>
 * Copyright (C) 2016
 */

/**
 * Get Restaurant list and their chain
 */
public class RestaurantListInteractorImpl implements RestaurantListInteractor {

    private static final String SEP = ",";
    private static final String AT = " at ";
    private static final String NEW_LINE = "\n";

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
                                rest.cuisineFormatted = "";
                                final List<String> cuisines = rest.getCuisine();
                                for (String cuisine : cuisines) {
                                    rest.cuisineFormatted += cuisine + SEP;
                                }
                                rest.cuisineFormatted = rest.cuisineFormatted.substring(0, rest.cuisineFormatted.length() - 1);

                                final List<Restaurant.Restaurants> chain = rest.getChain();
                                if (chain != null && chain.size() > 0) {
                                    rest.chainRestuarntFormatted = "";
                                    for (Restaurant.Restaurants restaurants1 : chain) {
                                        rest.chainRestuarntFormatted += restaurants1.getName() + AT + restaurants1.getCity() + NEW_LINE;
                                    }
                                }
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
