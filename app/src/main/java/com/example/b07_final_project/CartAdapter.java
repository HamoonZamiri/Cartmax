package com.example.b07_final_project;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;
    private CartActivity activity;

    protected class CartViewHolder extends RecyclerView.ViewHolder{
        protected TextView itemNameTextView;
        protected TextView itemPriceTextView;
        protected TextView itemTotalTextView;
        protected EditText itemCountTextView;
        protected Button increaseProductCountButton;
        protected Button decreaseProductCountButton;
        protected Button removeFromCartButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            this.itemNameTextView = (TextView) itemView.findViewById(R.id.productName);
            this.itemPriceTextView = (TextView) itemView.findViewById(R.id.productPrice);
            this.itemTotalTextView = (TextView) itemView.findViewById(R.id.productTotal);
            this.itemCountTextView = (EditText) itemView.findViewById(R.id.productCount);
            this.increaseProductCountButton = (Button) itemView.findViewById(R.id.increaseProductCount);
            this.decreaseProductCountButton = (Button) itemView.findViewById(R.id.decreaseProductCount);
            this.removeFromCartButton = (Button) itemView.findViewById(R.id.removeFromCartButton);
        }
    }

    public CartAdapter(ArrayList<CartItem> cartItems, CartActivity activity){
        this.cartItems = cartItems;
        this.activity = activity;
        if (cartItems.size() == 0){
            activity.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        }else{
            activity.findViewById(R.id.empty_view).setVisibility(View.GONE);
        }
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
        itemPriceTextView.setText(String.format("%.2f", item.getPrice()));

        TextView itemTotalTextView = holder.itemTotalTextView;
        Double totalPrice = Math.round(item.getPrice() * item.count * 100.0) / 100.0;
        itemTotalTextView.setText("$" + String.format("%.2f",totalPrice));

        TextView emptyCartTextView = activity.findViewById(R.id.empty_view);
        if (cartItems.size() == 0){
            activity.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        }else{
            activity.findViewById(R.id.empty_view).setVisibility(View.GONE);
        }

        EditText itemCountTextView = holder.itemCountTextView;
        itemCountTextView.setText(String.valueOf(item.count));
        itemCountTextView.addTextChangedListener(new TextWatcher() {
            boolean ignore = false;
            String before;
            String after;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (ignore) return;
                before = s.toString();
                if (before.equals("")){
                    before = "0";
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ignore) return;
                after = s.toString();
                if (after.equals("")){
                    after = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ignore) return;
                if (Integer.parseInt(after) > 100) {
                    after = "100";
                    ignore = true;
                    holder.itemCountTextView.setText("100");
                    ignore = false;
                }
                item.count = Integer.parseInt(after);
                updateItemTotal(holder, item);
                updateOrderTotal();
                activity.getCartRecyclerViewManager().updateDatabase(0);
            }
        });

        Button increaseProductCountButton = holder.increaseProductCountButton;
        increaseProductCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItemCount(holder,item.count + 1);
            }
        });
        Button decreaseProductCountButton = holder.decreaseProductCountButton;
        decreaseProductCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(itemCountTextView.getText().toString()) > 1) {
                    updateItemCount(holder, item.count -1);
                }
            }
        });

        Button removeFromCartButton = holder.removeFromCartButton;
        removeFromCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(holder.getAdapterPosition());
                if (cartItems.size() == 0){
                    emptyCartTextView.setVisibility(View.VISIBLE);
                }else{
                    emptyCartTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void removeItem(int position){
        cartItems.remove(position);
        notifyItemRemoved(position);

        updateOrderTotal();

        activity.getCartRecyclerViewManager().updateDatabase(1);
    }

    private void updateItemCount(CartViewHolder holder, int count){
        holder.itemCountTextView.setText(String.valueOf(count));
    }

    private void updateOrderTotal() {
        Double orderTotal = 0.0;
        for (CartItem i : cartItems){
            orderTotal += i.getPrice() * i.count;
        }
        TextView orderTotalTextView = (TextView) activity.findViewById(R.id.orderTotalValue);
        orderTotalTextView.setText("$" + String.format("%.2f", orderTotal));
    }

    private void updateItemTotal(CartViewHolder holder, CartItem item) {
        Double itemTotal = Math.round(item.getPrice() * item.count * 100.0)/100.0;
        holder.itemTotalTextView.setText("$" + String.format("%.2f", itemTotal));
    }


}
