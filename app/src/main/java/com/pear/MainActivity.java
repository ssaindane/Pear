package com.pear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JSONArray restaurantList;
    private RestaurantAdapter restaurantAdapter;
    private RestaurantViewModel restaurantViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        restaurantViewModel.getRestaurantlist();

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.restaurant_recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        restaurantViewModel.getRestaurantObserver().observe(this, new Observer<JSONArray>() {
            @Override
            public void onChanged(JSONArray jsonArray) {
                if(jsonArray != null){
                    restaurantList = jsonArray;
                    restaurantAdapter = new RestaurantAdapter(getApplicationContext(), restaurantList);
                    recyclerView.setAdapter(restaurantAdapter);
                    restaurantAdapter.setRestaurantList(restaurantList);
                }
            }
        });
    }
}