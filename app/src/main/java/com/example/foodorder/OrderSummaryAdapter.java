package com.example.foodorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private List<CartItem> cartItems;
    private OnQuantityChangeListener listener;

    // Interface to communicate item changes and removal to the activity/fragment
    public interface OnQuantityChangeListener {
        void onQuantityChanged(int position, int newQuantity, int itemPrice);
        void onItemRemoved(int position);
    }

    // Constructor for the adapter
    public OrderSummaryAdapter(List<CartItem> cartItems, OnQuantityChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the cart item for this position
        CartItem cartItem = cartItems.get(position);

        // Set the food name, price, and quantity
        holder.foodName.setText(cartItem.getFoodName());
        holder.foodPrice.setText("₹" + cartItem.getFoodPrice());
        holder.foodQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Handle "+" button click to increase quantity
        holder.buttonIncrease.setOnClickListener(v -> {
            int newQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(newQuantity);
            holder.foodQuantity.setText(String.valueOf(newQuantity));
            listener.onQuantityChanged(position, newQuantity, cartItem.getFoodPrice());
        });

        // Handle "-" button click to decrease quantity
        holder.buttonDecrease.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                int newQuantity = cartItem.getQuantity() - 1;
                cartItem.setQuantity(newQuantity);
                holder.foodQuantity.setText(String.valueOf(newQuantity));
                listener.onQuantityChanged(position, newQuantity, cartItem.getFoodPrice());
            }
        });

        // Handle "Remove" button click to remove the item
        holder.buttonRemove.setOnClickListener(v -> {
            // Remove the clicked item from the list
            cartItems.remove(position);

            // Notify the adapter that an item was removed
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size()); // Update the positions of remaining items

            // Notify the activity/fragment of the removal
            listener.onItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size(); // Return the size of the cart list
    }

    // ViewHolder class to hold the views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice, foodQuantity;
        AppCompatButton buttonIncrease, buttonDecrease, buttonRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize the views from the item layout
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}