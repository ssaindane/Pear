package com.pear;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private Context context;
    private JSONArray restaurantList;

    public RestaurantAdapter(Context context, JSONArray restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    public void setRestaurantList(JSONArray restaurantList) {
        this.restaurantList = restaurantList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.restaurant_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JSONObject restaurant;
        JSONArray batch;
        JSONObject items;
        ArrayList<String> itemData = new ArrayList<>();
        try {
            restaurant = (JSONObject) restaurantList.get(position);
            holder.name.setText(restaurant.getString("restaurant_name"));
            holder.address.setText(restaurant.getString("restaurant_location"));
            holder.amount.setText("\u20B9" + restaurant.getString("grand_total"));

            Glide.with(context)
                    .load(restaurant.getString("restaurant_image"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.restaurantImage);

            /*Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(restaurant.getString("restaurant_image"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.restaurantImage);*/
            holder.orderDate.setText(restaurant.getString("timestamp"));

            batch = restaurant.getJSONArray("batch");
            if (batch != null) {
                for (int i = 0; i < batch.length(); i++) {
                    items = batch.getJSONObject(i);
                    if (items != null) {
                        JSONArray item_object = items.getJSONArray("items");
                        if (item_object != null) {
                            for (int j = 0; j < item_object.length(); j++) {
                                JSONObject item = item_object.getJSONObject(j);
                                itemData.add(item.getInt("quantity") + " x " + item.getString("name"));
                            }
                            itemData.size();
                            String data = TextUtils.join(" , ", itemData);
                            holder.itemData.setText(data);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return restaurantList.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView restaurantImage;
        TextView name, address, itemData, orderDate, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            restaurantImage = mView.findViewById(R.id.restaurant_image);
            name = mView.findViewById(R.id.restaurant_name);
            address = mView.findViewById(R.id.restaurant_address);
            itemData = mView.findViewById(R.id.item_data);
            orderDate = mView.findViewById(R.id.order_date);
            amount = mView.findViewById(R.id.amount);

        }
    }
}
