package com.example.foodorder;

public class FoodItem {
    private String name;
    private String description;
    private double price;

    // Constructor
    public FoodItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}