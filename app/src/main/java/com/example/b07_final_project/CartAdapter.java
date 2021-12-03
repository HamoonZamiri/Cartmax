package com.example.b07_final_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private ArrayList<CartItem> cartItems;
    private Activity activity;

    protected class CartViewHolder extends RecyclerView.ViewHolder{
        protected TextView itemNameTextView;
        protected TextView itemPriceTextView;
        protected TextView itemTotalTextView;
        protected EditText itemCountTextView;
        protected Button increaseItemCountButton;
        protected Button decreaseItemCountButton;
        protected Button removeFromCartButton;
        protected TextView orderTotalTextView;

        public CartViewHolder(View itemView) {
            super(itemView);
            this.itemNameTextView = (TextView) itemView.findViewById(R.id.productName);
            this.itemPriceTextView = (TextView) itemView.findViewById(R.id.productPrice);
            this.itemTotalTextView = (TextView) itemView.findViewById(R.id.productTotal);
            this.orderTotalTextView = (TextView) itemView.findViewById(R.id.orderTotalValue);
            this.itemCountTextView = (EditText) itemView.findViewById(R.id.productCount);
            this.increaseItemCountButton = (Button) itemView.findViewById(R.id.increaseProductCount);
            this.decreaseItemCountButton = (Button) itemView.findViewById(R.id.decreaseProductCount);
            this.removeFromCartButton = (Button) itemView.findViewById(R.id.removeFromCartButton);
        }
    }

    public CartAdapter(ArrayList<CartItem> cartItems, Activity activity){
        this.cartItems = cartItems;
        this.activity = activity;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cartEntryView = inflater.inflate(R.layout.fragment_shopping_cart_entry, parent, false);
        return new CartViewHolder(cartEntryView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = (CartItem) cartItems.get(position);
        TextView itemNameTextView =  holder.itemNameTextView;
        itemNameTextView.setText(item.getBrand() + "'s " + item.getName());

        TextView itemPriceTextView = holder.itemPriceTextView;
        itemPriceTextView.setText(String.valueOf(item.getPrice()));

        TextView itemTotalTextView = holder.itemTotalTextView;
        Double totalPrice = item.getPrice() * item.count;
        itemTotalTextView.setText("$" + totalPrice);

        EditText itemCountTextView = holder.itemCountTextView;
        itemCountTextView.setText(String.valueOf(item.count));

        TextView orderTotalTextView = activity.findViewById(R.id.orderTotalValue);

        TextView emptyCartTextView = activity.findViewById(R.id.empty_view);

        Button increaseItemCountButton = holder.increaseItemCountButton;
        increaseItemCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCountTextView.setText(String.valueOf(Integer.parseInt(itemCountTextView.getText().toString())+ 1));
                Double itemTotal = (double)Math.round((item.getPrice() * Double.parseDouble(itemCountTextView.getText().toString())) * 100.0)/100.0;
                itemTotalTextView.setText("$" + itemTotal);
                double orderTotal = (double) Math.round((Double.parseDouble(orderTotalTextView.getText().toString()) + item.getPrice()) * 100.0)/100.0;
                orderTotalTextView.setText(String.valueOf(orderTotal));
            }
        });
        Button decreaseItemCountButton = holder.decreaseItemCountButton;
        decreaseItemCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(itemCountTextView.getText().toString()) > 1) {
                    Integer count = Integer.parseInt(itemCountTextView.getText().toString()) - 1;
                    itemCountTextView.setText(String.valueOf(count));
                    Double itemTotal = (double)Math.round((item.getPrice() * Double.parseDouble(itemCountTextView.getText().toString())) * 100.0)/100.0;
                    itemTotalTextView.setText("$" + itemTotal);
                    double orderTotal = (double) Math.round((Double.parseDouble(orderTotalTextView.getText().toString()) - item.getPrice()) * 100.0)/100.0;
                    orderTotalTextView.setText(String.valueOf(orderTotal));
                }
            }
        });

        Button removeFromCartButton = holder.removeFromCartButton;
        removeFromCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeItem(holder.getAdapterPosition());
                double itemTotal = item.getPrice() * Double.parseDouble(itemCountTextView.getText().toString());
                double orderTotal = (double) Math.round((Double.parseDouble(orderTotalTextView.getText().toString()) - itemTotal) * 100.0)/100.0;
                orderTotalTextView.setText(String.valueOf(orderTotal));
                if (cartItems.size() == 0){
                    emptyCartTextView.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void removeItem(int position){
        cartItems.remove(position);
        notifyItemRemoved(position);
    }
}
