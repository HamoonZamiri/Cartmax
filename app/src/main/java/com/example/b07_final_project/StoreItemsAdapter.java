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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreItemsAdapter extends RecyclerView.Adapter<StoreItemsAdapter.ItemViewHolder> {

    private ArrayList<Item> storeItems;
    private ArrayList<Item> cartItems;
    private final StoreActivity activity;

    protected class ItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView itemNameTextView;
        protected TextView itemPriceTextView;
        protected TextView itemTotalTextView;
        protected EditText itemCountTextView;
        protected Button increaseProductCountButton;
        protected Button decreaseProductCountButton;
        protected Button addToCartButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemNameTextView = (TextView) itemView.findViewById(R.id.productName);
            this.itemPriceTextView = (TextView) itemView.findViewById(R.id.productPrice);
            this.itemTotalTextView = (TextView) itemView.findViewById(R.id.productTotal);
            this.itemCountTextView = (EditText) itemView.findViewById(R.id.productCount);
            this.increaseProductCountButton = (Button) itemView.findViewById(R.id.increaseProductCount);
            this.decreaseProductCountButton = (Button) itemView.findViewById(R.id.decreaseProductCount);
            this.addToCartButton = (Button) itemView.findViewById(R.id.addToCartButton);
        }
    }

    public StoreItemsAdapter(ArrayList<Item> storeItems, StoreActivity activity){
        this.storeItems = storeItems;
        this.cartItems = new ArrayList<Item>();
        this.activity = activity;
        if (storeItems.size() == 0){
            activity.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        }else{
            activity.findViewById(R.id.empty_view).setVisibility(View.GONE);
        }
    }

    @Override
    public StoreItemsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listEntryView = inflater.inflate(R.layout.fragment_store_item, parent, false);
        return new StoreItemsAdapter.ItemViewHolder(listEntryView);
    }

    @Override
    public void onBindViewHolder(StoreItemsAdapter.ItemViewHolder holder, int position) {
        Item item = (Item) storeItems.get(position);
        TextView itemNameTextView =  holder.itemNameTextView;
        itemNameTextView.setText(item.getBrand() + "'s " + item.getName());

        TextView itemPriceTextView = holder.itemPriceTextView;
        itemPriceTextView.setText(String.format("%.2f", item.getPrice()));

        TextView itemTotalTextView = holder.itemTotalTextView;
        itemTotalTextView.setText("$" + String.format("%.2f",item.getPrice()));

        TextView emptyListTextView = activity.findViewById(R.id.empty_view);
        if (storeItems.size() == 0){
            activity.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        }else{
            activity.findViewById(R.id.empty_view).setVisibility(View.GONE);
        }

        EditText itemCountTextView = holder.itemCountTextView;
        itemCountTextView.setText("1");
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
                updateItemTotal(holder, item, Integer.parseInt(after));
            }
        });

        Button increaseProductCountButton = holder.increaseProductCountButton;
        increaseProductCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItemCount(holder,Integer.parseInt(itemCountTextView.getText().toString()) + 1);
            }
        });
        Button decreaseProductCountButton = holder.decreaseProductCountButton;
        decreaseProductCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(itemCountTextView.getText().toString()) > 1) {
                    updateItemCount(holder, Integer.parseInt(itemCountTextView.getText().toString()) -1);
                }
            }
        });

        Button addToCartButton = holder.addToCartButton;
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(item, Integer.parseInt(itemCountTextView.getText().toString()));
                if (storeItems.size() == 0){
                    emptyListTextView.setVisibility(View.VISIBLE);
                }else{
                    emptyListTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeItems.size();
    }

    private void addToCart(Item item, int count){
        int index = cartItems.indexOf(item);
        if (index == -1){
            Item temp = new Item(item.getName(), item.getBrand(), item.getPrice(),
                    item.getDescription(), count);
            cartItems.add(temp);
        }else{
            cartItems.get(index).setQuantity(cartItems.get(index).getQuantity() + count);
        }
        Toast toast = Toast.makeText(activity, "Successfully added to cart!", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void updateItemCount(StoreItemsAdapter.ItemViewHolder holder, int count){
        holder.itemCountTextView.setText(String.valueOf(count));
    }

    private void updateItemTotal(StoreItemsAdapter.ItemViewHolder holder, Item item, int count) {
        Double itemTotal = Math.round(item.getPrice() * count * 100.0)/100.0;
        holder.itemTotalTextView.setText("$" + String.format("%.2f", itemTotal));
    }

    public ArrayList<Item> getCart() {
        return cartItems;
    }

}
