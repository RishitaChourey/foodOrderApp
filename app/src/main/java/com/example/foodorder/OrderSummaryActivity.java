package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderSummaryActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Get views
        TextView tvOrderDetails = findViewById(R.id.tvOrderDetails);
        TextView tvTotalBill = findViewById(R.id.tvTotalBill);
        Button btnBackToMenu = findViewById(R.id.btnBackToMenu);
        Button btnconfirm = findViewById(R.id.btnconfirm);

        // Get order details from intent
        Intent intent = getIntent();
        String orderDetails = intent.getStringExtra("orderDetails");
        int totalBill = intent.getIntExtra("totalBill", 0);

        // Display order details and total bill
        tvOrderDetails.setText(orderDetails.isEmpty() ? "No orders placed." : orderDetails);
        tvTotalBill.setText("Total Bill: ₹" + totalBill);

        // Save order to SQLite
        btnconfirm.setOnClickListener(v -> {
            if (!orderDetails.isEmpty()) {
                saveOrderToDatabase(orderDetails, totalBill);
            } else {
                Toast.makeText(this, "No orders to save.", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button to return to ShowMenuActivity
        btnBackToMenu.setOnClickListener(v -> {
            Intent backIntent = new Intent(OrderSummaryActivity.this, MenuActivity.class);
            startActivity(backIntent);
            finish();
        });
    }

    private void saveOrderToDatabase(String orderDetails, int totalBill) {
        // Get the current timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Save the order to SQLite
        dbHelper.insertOrder(orderDetails, totalBill, timestamp);

        Toast.makeText(this, "Order saved successfully!", Toast.LENGTH_SHORT).show();
    }
}