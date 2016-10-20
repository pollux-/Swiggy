package com.pollux.swiggy.view;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2015 YMediaLabs
 */
public interface AbsView {
    void onRequestSubmitted();
    void onRequestComplete();
    void onError(final String message);
}
