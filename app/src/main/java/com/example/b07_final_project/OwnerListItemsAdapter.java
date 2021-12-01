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

    List<String> lst_names;
    List<String> lst_brands;
    List<String> lst_descriptions;
    List<Integer> lst_quantities;
    Context context;

    public OwnerListItemsAdapter(Context ct, List<String> names, List<String> brands,
                                 List<String> descriptions, List<Integer> myQuantities) {
        lst_names = names;
        lst_brands = brands;
        lst_descriptions = descriptions;
        lst_quantities = myQuantities;
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
        holder.itemName.setText(lst_names.get(position));
        holder.itemBrand.setText(lst_brands.get(position));
        holder.itemDescription.setText(lst_descriptions.get(position));
        holder.itemQuantity.setText(lst_quantities.get(position));
    }

    @Override
    public int getItemCount() {
        return lst_names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemBrand, itemDescription, itemQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(Objects.requireNonNull(itemView));
            itemName = itemView.findViewById(R.id.itemName);
            itemBrand = itemView.findViewById(R.id.itemBrand);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemQuantity = itemView.findViewById(R.id.itemQty);
        }
    }
}
