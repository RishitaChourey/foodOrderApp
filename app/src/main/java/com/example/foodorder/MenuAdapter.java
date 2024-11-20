package com.example.foodorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.CartViewHolder> {

    private List<String> menuItems;          // List of food names
    private List<String> foodPrices;         // List of food prices or descriptions
    private List<Integer> cartItemImages;    // List of image resources for each item
    private int[] itemQuantities;            // Array to store the quantity of each item

    // Constructor to initialize the lists
    public MenuAdapter(List<String> cartItems, List<String> foodPrices, List<Integer> cartItemImages) {
        this.menuItems = cartItems;
        this.foodPrices = foodPrices;  // Changed the variable name here
        this.cartItemImages = cartItemImages;
        this.itemQuantities = new int[cartItems.size()];
        for (int i = 0; i < cartItems.size(); i++) {
            itemQuantities[i] = 0; // Initialize quantity to 1 for each item
        }
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for a single item in the list
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false); // Replace with your actual layout file

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    // ViewHolder class for the CartAdapter
    public class CartViewHolder extends RecyclerView.ViewHolder {

        // Define the views from the layout
        private TextView foodName;
        private TextView cartItemPrice;
        private ImageView cartItemImage;
        private TextView cartItemQuantity;
        private Button increaseButton;
        private Button decreaseButton;

        // Constructor to initialize the views
        public CartViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.textView2);  // Food name TextView
            cartItemPrice = itemView.findViewById(R.id.cartitemprice);  // Food price description
            cartItemImage = itemView.findViewById(R.id.imageView3);  // Food image ImageView
            cartItemQuantity = itemView.findViewById(R.id.quantityText);  // Quantity TextView
            increaseButton = itemView.findViewById(R.id.buttonPlus);  // "+" Button
            decreaseButton = itemView.findViewById(R.id.buttonMinus);  // "-" Button
        }

        // Bind data to the views
        public void bind(int position) {
            // Set the food name, price description, and image resource
            foodName.setText(menuItems.get(position));  // Food name
            cartItemPrice.setText(foodPrices.get(position));  // Food price or description
            cartItemImage.setImageResource(cartItemImages.get(position));  // Food image
            cartItemQuantity.setText(String.valueOf(itemQuantities[position]));  // Quantity text

            // Handle the quantity increase button click
            increaseButton.setOnClickListener(v -> {
                itemQuantities[position]++;
                cartItemQuantity.setText(String.valueOf(itemQuantities[position]));
            });

            // Handle the quantity decrease button click
            decreaseButton.setOnClickListener(v -> {
                if (itemQuantities[position] > 1) { // Ensure the quantity doesn't go below 1
                    itemQuantities[position]--;
                    cartItemQuantity.setText(String.valueOf(itemQuantities[position]));
                }
            });
        }
    }
}