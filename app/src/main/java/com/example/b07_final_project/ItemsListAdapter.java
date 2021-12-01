package com.example.b07_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ItemsViewHolder>{

    private Context context;
    ArrayList<String> names;
    ArrayList<String> brands;
    ArrayList<Integer> prices;

    public ItemsListAdapter(Context context, ArrayList<String> names, ArrayList<String> brands,
                            ArrayList<Integer> prices) {
        this.context = context;
        this.names = names;
        this.brands = brands;
        this.prices = prices;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_column,parent,false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        holder.itemName.setText(names.get(position));
        holder.itemBrand.setText(brands.get(position));
        holder.itemPrice.setText("$" + String.valueOf(prices.get(position)));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemBrand;
        TextView itemPrice;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemBrand = itemView.findViewById(R.id.itemBrand);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
