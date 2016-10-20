package com.pollux.swiggy.model;

import java.util.List;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2016
 */
public class Restaurant {

    public List<Restaurants> restaurants;

    public List<Restaurants> getRestaurants() {
        return restaurants;
    }

    public class Restaurants {

        public String name;
        public String city;
        public String area;
        public String avg_rating;
        public String cid;
        public List<String> cuisine;
        public String cuisineFormatted;
        public String costForTwo;
        public List<Restaurants> chain;

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        public String getArea() {
            return area;
        }

        public String getAvgRating() {
            return avg_rating;
        }

        public String getCid() {
            return cid;
        }

        public List<String> getCuisine() {
            return cuisine;
        }

        public String getCostForTwo() {
            return costForTwo;
        }

        public List<Restaurants> getChain() {
            return chain;
        }

        public String getCuisineFormatted() {
            return cuisineFormatted;
        }
    }

}

