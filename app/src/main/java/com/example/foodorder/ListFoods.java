package com.example.foodorder;
import com.example.foodorder.FoodItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListFoods extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_foods); // Assuming your layout is activity_list_foods

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.FoodListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of food items
        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem("Chai", "A hot cup of tea", 20));
        foodItems.add(new FoodItem("Samosa", "Crispy snack with filling", 15));
        foodItems.add(new FoodItem("Pav Bhaji", "Spicy mashed vegetables with bread", 40));
        foodItems.add(new FoodItem("Cold Drink", "Refreshing cold drink", 30));
        foodItems.add(new FoodItem("Ice Cream", "Sweet frozen dessert", 25));

        // Create the FoodAdapter and set it to the RecyclerView
        foodAdapter = new FoodAdapter(foodItems, new FoodAdapter.OnFoodClickListener() {
            @Override
            public void onFoodClick(FoodItem foodItem) {
                // Handle food item click (for example, show a Toast message)
                Toast.makeText(ListFoods.this, "Clicked on: " + foodItem.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(foodAdapter);
    }
}