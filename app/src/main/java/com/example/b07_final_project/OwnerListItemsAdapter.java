package com.example.b07_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class OwnerListItemsAdapter extends RecyclerView.Adapter<OwnerListItemsAdapter.MyViewHolder> {

    String[] lst_names;
    String[] lst_brands;
    String[] lst_descriptions;
    Integer[] lst_quantities;
    Integer[] lst_prices;
    Context context;

    public OwnerListItemsAdapter(Context ct, String[] names, String[] brands,
                                 String[] descriptions, Integer[] myQuantities, Integer[] myPrices) {
        lst_names = names;
        lst_brands = brands;
        lst_descriptions = descriptions;
        lst_quantities = myQuantities;
        lst_prices = myPrices;
        context = ct;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemName.setText(lst_names[position]);
        holder.itemBrand.setText(lst_brands[position]);
        holder.itemDescription.setText(lst_descriptions[position]);
        holder.itemQuantity.setText("Qty: " + String.valueOf(lst_quantities[position]));
        holder.itemPrice.setText("Price: " + String.valueOf(lst_prices[position]));
    }

    @Override
    public int getItemCount() {
        return lst_names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemBrand, itemDescription, itemQuantity, itemPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(Objects.requireNonNull(itemView));
            itemName = itemView.findViewById(R.id.itemName);
            itemBrand = itemView.findViewById(R.id.itemBrand);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemQuantity = itemView.findViewById(R.id.itemQty);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
