package com.pollux.swiggy.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.pollux.swiggy.R;
import com.pollux.swiggy.adapter.LoadingDummyAdapter;
import com.pollux.swiggy.adapter.RestaurantAdapter;
import com.pollux.swiggy.model.Restaurant;
import com.pollux.swiggy.presenter.RestaurantListPresenter;
import com.pollux.swiggy.presenter.RestaurantListPresenterImpl;
import com.pollux.swiggy.view.RestaurantView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sree Kumar
 * <p>
 * Copyright (C) 2016
 */
public class HomeFragment extends Fragment implements RestaurantView {
    private static final String TAG = "HomeFragment";
    public static final int ITEM_HEIGHT = 130;
    public static final int START_DELAY = 2000;
    private RestaurantListPresenter mPresenter;
    private RecyclerView mList;
    private ShimmerFrameLayout mShimmerFrameLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private int mPageNo = 1;
    private boolean mDownloadInFlight;
    private View mProgess;
    private List<Restaurant.Restaurants> mResturants;
    private RestaurantAdapter mAdapter;


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        mList = (RecyclerView) view.findViewById(R.id.list);
        mShimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer);
        mProgess = view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mResturants = new ArrayList<>();
        mPresenter = new RestaurantListPresenterImpl(this);
        mPresenter.getRestaurantList(mPageNo);
        setDummyAdapter();

    }

    private void setAdapter(Restaurant restaurants) {
        mResturants.addAll(restaurants.getRestaurants());
        if (mAdapter == null) {
            mAdapter = new RestaurantAdapter(getActivity(), mResturants);
            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mList.setLayoutManager(mLinearLayoutManager);
            mList.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();

        }
    }

    /**
     * Callback to Activity to inform completion of fetching
     * Restaurant.
     *
     * @param restaurants
     */
    @Override
    public void onDownloadRestaurantDetails(@NonNull Restaurant restaurants) {
        Log.d(TAG, "onDownloadRestaurantDetails " + restaurants);
        setAdapter(restaurants);
        addPaginationListener();
        mPageNo++;
    }

    @Override
    public void onRequestSubmitted() {
        Log.d(TAG, "onRequestSubmitted ");
        mDownloadInFlight = true;
        if(mResturants.size() > 0){
            mProgess.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestComplete() {
        mShimmerFrameLayout.stopShimmerAnimation();
        mDownloadInFlight = false;
        mProgess.setVisibility(View.GONE);

    }

    @Override
    public void onError(String message) {
        Log.e(TAG, "On error " + message);
        mProgess.setVisibility(View.GONE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();

    }

    protected void setDummyAdapter() {
        mList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int count = (int) (mList.getHeight() /
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ITEM_HEIGHT,
                                getActivity().getResources().getDisplayMetrics()));
                mList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mList.setLayoutManager(mLinearLayoutManager);
                mList.setAdapter(new LoadingDummyAdapter(getActivity(), count));
                mShimmerFrameLayout.setDuration(START_DELAY);
                mShimmerFrameLayout.startShimmerAnimation();
            }
        });


    }


    private void addPaginationListener() {
        mList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                    if (!mDownloadInFlight) {
                        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                            mPresenter.getRestaurantList(mPageNo);

                        }
                    }
                }
            }
        });
    }

}
