package com.example.b07_final_project;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemViewHolder> {

    private ArrayList<Item> cartItems;
    private final CartActivity activity;

    protected class ItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView itemNameTextView;
        protected TextView itemPriceTextView;
        protected TextView itemTotalTextView;
        protected EditText itemCountTextView;
        protected Button increaseProductCountButton;
        protected Button decreaseProductCountButton;
        protected Button removeFromCartButton;

        public ItemViewHolder(View itemView) {
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

    public CartAdapter(ArrayList<Item> listItems, CartActivity activity){
        this.cartItems = listItems;
        this.activity = activity;
        if (listItems.size() == 0){
            activity.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        }else{
            activity.findViewById(R.id.empty_view).setVisibility(View.GONE);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cartEntryView = inflater.inflate(R.layout.fragment_shopping_cart_entry, parent, false);
        return new ItemViewHolder(cartEntryView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = (Item) cartItems.get(position);
        TextView itemNameTextView =  holder.itemNameTextView;
        itemNameTextView.setText(item.getBrand() + "'s " + item.getName());

        TextView itemPriceTextView = holder.itemPriceTextView;
        itemPriceTextView.setText(String.format("%.2f", item.getPrice()));

        TextView itemTotalTextView = holder.itemTotalTextView;
        Double totalPrice = Math.round(item.getPrice() * item.getQuantity() * 100.0) / 100.0;
        itemTotalTextView.setText("$" + String.format("%.2f",totalPrice));

        TextView emptyListTextView = activity.findViewById(R.id.empty_view);
        if (cartItems.size() == 0){
            activity.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        }else{
            activity.findViewById(R.id.empty_view).setVisibility(View.GONE);
        }

        EditText itemCountTextView = holder.itemCountTextView;
        itemCountTextView.setText(String.valueOf(item.getQuantity()));
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
                item.setQuantity(Integer.parseInt(after));
                updateItemTotal(holder, item);
                updateOrderTotal();
            }
        });

        Button increaseProductCountButton = holder.increaseProductCountButton;
        increaseProductCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItemCount(holder,item.getQuantity() + 1);
            }
        });
        Button decreaseProductCountButton = holder.decreaseProductCountButton;
        decreaseProductCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(itemCountTextView.getText().toString()) > 1) {
                    updateItemCount(holder, item.getQuantity() -1);
                }
            }
        });

        Button updateCartButton = holder.removeFromCartButton;
        updateCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromCart(holder.getAbsoluteAdapterPosition());
                if (cartItems.size() == 0){
                    emptyListTextView.setVisibility(View.VISIBLE);
                }else{
                    emptyListTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    protected void removeFromCart(int position){
        cartItems.remove(position);
        notifyItemRemoved(position);
        updateOrderTotal();
    }

    private void updateItemCount(ItemViewHolder holder, int count){
        holder.itemCountTextView.setText(String.valueOf(count));
    }

    protected void updateOrderTotal() {
        Double orderTotal = 0.0;
        for (Item i : cartItems){
            orderTotal += i.getPrice() * i.getQuantity();
        }
        TextView orderTotalTextView = (TextView) activity.findViewById(R.id.orderTotalValue);
        orderTotalTextView.setText("$" + String.format("%.2f", orderTotal));
    }

    private void updateItemTotal(ItemViewHolder holder, Item item) {
        Double itemTotal = Math.round(item.getPrice() * item.getQuantity() * 100.0)/100.0;
        holder.itemTotalTextView.setText("$" + String.format("%.2f", itemTotal));
    }

    public ArrayList<Item> getCart(){
        return cartItems;
    }
}
