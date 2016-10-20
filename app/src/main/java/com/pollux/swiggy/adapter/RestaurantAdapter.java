package com.pollux.swiggy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pollux.swiggy.R;
import com.pollux.swiggy.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2016
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Restaurant.Restaurants> mRestaurantsList;
    private final LayoutInflater mLayoutInflater;


    public RestaurantAdapter(final Context context, final List<Restaurant.Restaurants> restaurantsList) {
        this.mRestaurantsList = restaurantsList;
        this.mLayoutInflater = LayoutInflater.from(context);

    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantHolder(mLayoutInflater.inflate(R.layout.resturant_list_item, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Restaurant.Restaurants restaurants = mRestaurantsList.get(position);
        if (restaurants != null) {

            RestaurantHolder restaurantHolder = (RestaurantHolder) holder;
            restaurantHolder.mName.setText(restaurants.name);
            restaurantHolder.mCuisines.setText(restaurants.getCuisineFormatted());
            restaurantHolder.mCost.setText(restaurants.getCostForTwo());
            restaurantHolder.mRating.setText(restaurants.getAvgRating());
            Picasso.with(restaurantHolder.mThumb.getContext())
                    .load("https://res.cloudinary.com/swiggy/image/upload/c_fill,f_auto,fl_lossy,h_"
                            +restaurantHolder.mThumb.getHeight()+",q_100,w_"
                            +restaurantHolder.mThumb.getWidth()+"/" + restaurants.cid)
                    .into(restaurantHolder.mThumb);
        }

    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mRestaurantsList != null ? mRestaurantsList.size() : 0;
    }


    private static class RestaurantHolder extends RecyclerView.ViewHolder {
        public ImageView mThumb;
        public TextView mName;
        public TextView mCuisines;
        public TextView mCost;
        public TextView mRating;

        public RestaurantHolder(View itemView) {
            super(itemView);
            mThumb = (ImageView) itemView.findViewById(R.id.thumb);
            mName = (TextView) itemView.findViewById(R.id.name);
            mCuisines = (TextView) itemView.findViewById(R.id.cuisine);
            mCost = (TextView) itemView.findViewById(R.id.cost);
            mRating = (TextView) itemView.findViewById(R.id.rating);
        }
    }
}
