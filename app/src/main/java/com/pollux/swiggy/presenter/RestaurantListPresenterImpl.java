package com.pollux.swiggy.presenter;

import com.pollux.swiggy.interactor.RestaurantListInteractor;
import com.pollux.swiggy.interactor.RestaurantListInteractorImpl;
import com.pollux.swiggy.model.Restaurant;
import com.pollux.swiggy.utils.RxUtils;
import com.pollux.swiggy.view.RestaurantView;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2016
 */
public class RestaurantListPresenterImpl implements RestaurantListPresenter {

    private RestaurantView mView;
    private final RestaurantListInteractor mRestaurantListInteractor;
    private CompositeSubscription mSubscription;

    public RestaurantListPresenterImpl(final RestaurantView view) {
        mView = view;
        mRestaurantListInteractor = new RestaurantListInteractorImpl();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getRestaurantList(int pageNo) {
        mView.onRequestSubmitted();
        mSubscription.add(mRestaurantListInteractor.getRestaurantListInteractorList(pageNo).subscribe(new Subscriber<Restaurant>() {
            @Override
            public void onCompleted() {
                mView.onRequestComplete();

            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onNext(Restaurant restaurants) {
                mView.onDownloadRestaurantDetails(restaurants);
            }
        }));


    }

    @Override
    public void onDestroy() {
        mView = null;
        RxUtils.unsubscribeIfNotNull(mSubscription);

    }
}
