package com.example.foodorder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        // Initialize RecyclerView
        recyclerViewCart = findViewById(R.id.cartRecyclerView);

        // Sample data for the adapter (replace with your actual data)
        List<String> cartItems = new ArrayList<>();
        cartItems.add("Aloo Pattice");
        cartItems.add("Thick Coffee");
        cartItems.add("Sandwich");
        cartItems.add("Fresh Juice");
        cartItems.add("Garam Chai");

        List<String> foodPrices = new ArrayList<>();
        foodPrices.add("Rs.18");
        foodPrices.add("Rs.30");
        foodPrices.add("Rs.40");
        foodPrices.add("Rs.30");
        foodPrices.add("Rs.12");

        List<Integer> cartItemImages = new ArrayList<>();
        cartItemImages.add(R.drawable.pattice);
        cartItemImages.add(R.drawable.coldcoffee);// Replace with actual image resource
        cartItemImages.add(R.drawable.chocolatesandwich);   // Replace with actual image resource

        cartItemImages.add(R.drawable.watermelonjuice);// Replace with actual image resource
        cartItemImages.add(R.drawable.chai);
        // Create an instance of the CartAdapter
        menuAdapter = new MenuAdapter(cartItems, foodPrices, cartItemImages);

        // Set RecyclerView layout manager and adapter
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(menuAdapter);
    }
}
