package com.pear;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestaurantAPI {

    @GET("order/user/previous_orders/Qu2cRybfWGMaki7eJtk2O0oxE3y2/")
    Call<ArrayList> getRestaurants();
}
