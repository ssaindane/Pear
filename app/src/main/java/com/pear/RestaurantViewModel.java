package com.pear;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantViewModel extends ViewModel {

    private MutableLiveData<JSONArray> restaurants;

    public RestaurantViewModel(){
        restaurants = new MutableLiveData<>();
    }

    public MutableLiveData<JSONArray> getRestaurantObserver(){
        return restaurants;
    }




    public void getRestaurantlist(){
        RestaurantAPI restaurantApis = RetrofitClientInstance.getRetrofitInstance().create(RestaurantAPI.class);
        Call<ArrayList> call = restaurantApis.getRestaurants();
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                if(response.isSuccessful()){
                    JSONArray repoData = new JSONArray(response.body());
                    repoData.length();
                    restaurants.setValue(repoData);
                    //initializeRecyclerView(repoData);
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
               // Toast.makeText(RestaurantViewModel.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }




}
