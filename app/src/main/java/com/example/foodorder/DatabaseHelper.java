package com.example.foodorder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Orders.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ORDERS = "previous_orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ORDER_DETAILS = "orderDetails";
    private static final String COLUMN_TOTAL_BILL = "totalBill";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_DETAILS + " TEXT, " +
                COLUMN_TOTAL_BILL + " INTEGER, " +
                COLUMN_TIMESTAMP + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    // Insert a new order
    public void insertOrder(String orderDetails, int totalBill, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_DETAILS, orderDetails);
        values.put(COLUMN_TOTAL_BILL, totalBill);
        values.put(COLUMN_TIMESTAMP, timestamp);

        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }

    // Retrieve all orders
    public ArrayList<String> getAllOrders() {
        ArrayList<String> ordersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORDERS, null, null, null, null, null, COLUMN_ID + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String orderDetails = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_DETAILS));
                int totalBill = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_BILL));
                String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));

                ordersList.add(orderDetails + "\nTotal: ₹" + totalBill + "\nDate: " + timestamp);
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        db.close();
        return ordersList;
    }
}