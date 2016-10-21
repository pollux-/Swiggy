package com.pollux.swiggy.view;

/**
 * Created by Sree Kumar
 * <p>
 * Copyright (C) 2015 Sree Kumar
 */
public interface AbsView {
    void onRequestSubmitted();
    void onRequestComplete();
    void onError(final String message);
}
