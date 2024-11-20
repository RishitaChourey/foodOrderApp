package com.example.foodorder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodItems;
    private OnFoodClickListener listener;

    // Constructor
    public FoodAdapter(List<FoodItem> foodItems, OnFoodClickListener listener) {
        this.foodItems = foodItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for the food list
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_layout, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        // Set data for the food item
        FoodItem foodItem = foodItems.get(position);
        holder.nameTextView.setText(foodItem.getName());
        holder.priceTextView.setText("₹" + foodItem.getPrice());
        holder.itemView.setOnClickListener(v -> listener.onFoodClick(foodItem)); // Handle click
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    // Define the ViewHolder class to represent each food item
    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView priceTextView;

        public FoodViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.foodName);
            priceTextView = itemView.findViewById(R.id.foodPrice);
        }
    }

    // Define the OnFoodClickListener interface
    public interface OnFoodClickListener {
        void onFoodClick(FoodItem foodItem);  // Method to handle item click
    }
}