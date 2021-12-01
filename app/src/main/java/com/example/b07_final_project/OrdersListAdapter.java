package com.example.b07_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrdersViewHolder> {
    private Context context;
    ArrayList<String> storeNames;
    ArrayList<String> statuses;
    ArrayList<ArrayList<Item>> itemsList;

    public OrdersListAdapter(Context context, ArrayList<String> storeNames,
                             ArrayList<String> statuses,
                             ArrayList<ArrayList<Item>> itemsList) {
        this.context = context;
        this.storeNames = storeNames;
        this.statuses = statuses;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_row,parent,false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        holder.storeName.setText("Store: " + storeNames.get(position));
        holder.status.setText("Status: " + statuses.get(position));

        ArrayList<Item> items = itemsList.get(position);
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> brands = new ArrayList<String>();
        ArrayList<Integer> prices = new ArrayList<Integer>();
        for(Item i : items) {
            names.add(i.getName());
            brands.add(i.getBrand());
            prices.add(i.getPrice());
        }

        ItemsListAdapter adapter = new ItemsListAdapter(this.context, names, brands, prices);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,
                false);
        holder.itemsRV.setAdapter(adapter);
        holder.itemsRV.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView status;
        RecyclerView itemsRV;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName = (TextView) itemView.findViewById(R.id.orderStoreName);
            status = (TextView) itemView.findViewById(R.id.orderStatus);
            itemsRV = (RecyclerView) itemView.findViewById(R.id.itemsRV);
        }
    }
}
