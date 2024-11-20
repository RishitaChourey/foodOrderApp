package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ShowMenuActivity extends AppCompatActivity {

    // Data structure to store the menu items and their quantities
    private HashMap<String, Integer> orderMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        orderMap = new HashMap<>(); // Initialize order map

        // Example for Chai (Repeat for other items)
        Button btnMinus1 = findViewById(R.id.btnMinus1);
        Button btnPlus1 = findViewById(R.id.btnPlus1);
        TextView tvQuantity1 = findViewById(R.id.tvQuantity1);

        String itemName1 = "Chai (Tea)";
        int itemPrice1 = 20;

        // Initialize the quantity to 0
        orderMap.put(itemName1, 0);

        // Set click listeners for "+" and "-" buttons
        btnPlus1.setOnClickListener(v -> {
            int quantity = orderMap.get(itemName1);
            quantity++;
            orderMap.put(itemName1, quantity);
            tvQuantity1.setText(String.valueOf(quantity));
        });

        btnMinus1.setOnClickListener(v -> {
            int quantity = orderMap.get(itemName1);
            if (quantity > 0) {
                quantity--;
                orderMap.put(itemName1, quantity);
            }
            tvQuantity1.setText(String.valueOf(quantity));
        });

        // Place Order Button
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(v -> {
            StringBuilder orderDetails = new StringBuilder();
            int totalBill = 0;

            // Generate order summary
            for (HashMap.Entry<String, Integer> entry : orderMap.entrySet()) {
                String item = entry.getKey();
                int quantity = entry.getValue();
                if (quantity > 0) {
                    int price = itemPrice1 * quantity; // Replace itemPrice1 with a map for dynamic pricing
                    totalBill += price;
                    orderDetails.append(item)
                            .append(" x")
                            .append(quantity)
                            .append(" - ₹")
                            .append(price)
                            .append("\n");
                }
            }

            // Pass the order details to OrderSummaryActivity
            Intent intent = new Intent(ShowMenuActivity.this, OrderSummaryActivity.class);
            intent.putExtra("orderDetails", orderDetails.toString());
            intent.putExtra("totalBill", totalBill);
            startActivity(intent);
        });
    }
}