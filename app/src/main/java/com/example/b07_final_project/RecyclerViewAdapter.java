package com.example.b07_final_project;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<CartItem> cartItems;

    protected class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView itemImageView;
        protected TextView itemNameTextView;
        protected TextView itemPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemImageView = (ImageView) itemView.findViewById(R.id.productImage);
            this.itemNameTextView = (TextView) itemView.findViewById(R.id.productName);
            this.itemPriceTextView = (TextView) itemView.findViewById(R.id.productTotal);
        }
    }

    public RecyclerViewAdapter(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View cartEntryView = inflater.inflate(R.layout.fragment_shopping_cart_entry, parent, false);

        return new ViewHolder(cartEntryView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        TextView itemNameTextView =  holder.itemNameTextView;
        itemNameTextView.setText(item.getName());

        TextView itemPriceTextView = holder.itemPriceTextView;
        Double totalPrice = item.getPrice() * (double) item.count;
        itemPriceTextView.setText(totalPrice.toString());

        ImageView itemImageView = holder.itemImageView;
        String itemImage = item.getImage();
        //itemImageView.setImageResource(getResources().getIdentifier(itemImage, "drawable", "res"));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
