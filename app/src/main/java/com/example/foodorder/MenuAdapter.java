package com.example.foodorder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements android.widget.Filterable {

    private List<String> menuItems;
    private List<String> menuItemsFiltered; // Filtered list for search
    private List<String> foodPrices;
    private List<Integer> foodImages;
    private int[] itemQuantities;
    private OnAddClickListener onAddClickListener;

    public interface OnAddClickListener {
        void onAddClick(String itemName, String itemPrice, int quantity);
    }

    public MenuAdapter(List<String> menuItems, List<String> foodPrices, List<Integer> foodImages, OnAddClickListener onAddClickListener) {
        this.menuItems = menuItems;
        this.menuItemsFiltered = new ArrayList<>(menuItems); // Initially, filtered list is the same as menu items
        this.foodPrices = foodPrices;
        this.foodImages = foodImages;
        this.onAddClickListener = onAddClickListener;
        this.itemQuantities = new int[menuItems.size()];
        for (int i = 0; i < menuItems.size(); i++) {
            itemQuantities[i] = 0;
        }
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return menuItemsFiltered.size(); // Use the filtered list size
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView foodName, foodPrice, quantityText;
        private ImageView foodImage;
        private Button addButton, increaseButton, decreaseButton;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodImage = itemView.findViewById(R.id.imageView3);
            quantityText = itemView.findViewById(R.id.foodQuantity);
            addButton = itemView.findViewById(R.id.buttonRemove);
            increaseButton = itemView.findViewById(R.id.buttonIncrease);
            decreaseButton = itemView.findViewById(R.id.buttonDecrease);
        }

        public void bind(int position) {
            foodName.setText(menuItemsFiltered.get(position));
            foodPrice.setText(foodPrices.get(position));
            foodImage.setImageResource(foodImages.get(position));
            quantityText.setText(String.valueOf(itemQuantities[position]));

            increaseButton.setOnClickListener(v -> {
                itemQuantities[position]++;
                quantityText.setText(String.valueOf(itemQuantities[position]));
            });

            decreaseButton.setOnClickListener(v -> {
                if (itemQuantities[position] > 0) {
                    itemQuantities[position]--;
                    quantityText.setText(String.valueOf(itemQuantities[position]));
                }
            });

            addButton.setOnClickListener(v -> {
                if (itemQuantities[position] > 0) {
                    onAddClickListener.onAddClick(menuItemsFiltered.get(position), foodPrices.get(position), itemQuantities[position]);
                } else {
                    Toast.makeText(itemView.getContext(), "Please increase the quantity before adding!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Filter method to filter the items based on the search query
    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<String> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(menuItems); // If query is empty, show all items
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (String item : menuItems) {
                        if (item.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item); // Add items that match the search query
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                menuItemsFiltered.clear();
                if (results.values != null) {
                    menuItemsFiltered.addAll((List) results.values); // Update filtered list
                }
                notifyDataSetChanged(); // Notify adapter that data has changed
            }
        };
    }
}