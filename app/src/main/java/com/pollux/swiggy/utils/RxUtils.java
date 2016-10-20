package com.pollux.swiggy.utils;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RxUtils {

    private RxUtils() {
    }

    private static final Transformer<Observable, Observable> schedulersTransformer = new Transformer<Observable, Observable>() {
        @Override
        public Observable<Observable> call(Observable<Observable> observableObservable) {

            return observableObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

        }
    };


    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }

    @SuppressWarnings("unchecked")
    public static <T> Transformer<T, T> applySchedulers() {
        return (Transformer<T, T>) schedulersTransformer;
    }
}