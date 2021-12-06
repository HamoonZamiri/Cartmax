package com.example.b07_final_project;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartDataManager extends DataManager<Item> implements ValueEventListener  {
    // manages data inside of cart recyclerView by reading and writing to firebase db

    public CartDataManager(User user, Activity activity){
        this.activity = activity;
        this.data = new ArrayList<Item>();

        // single use event listener to find which user's cart to access
        this.ref = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    String temp = child.child("email").getValue().toString();
                    if (temp.equals(user.getEmail())){
                        UID = child.getKey().toString();
                        addCartListener();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database error", error.toException().toString());
            }
        });
    }

    public ArrayList<Item> getData(){
        return data;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        data.clear();

        for (DataSnapshot child : snapshot.getChildren()){
            data.add(child.getValue(Item.class));
        }
        CartActivity activity = (CartActivity) this.activity;
        activity.getAdapter().notifyDataSetChanged();

        TextView orderTotalTextView = activity.findViewById(R.id.orderTotalValue);
        Double orderTotal = 0.0;
        for (Item item : data) {
            orderTotal += item.getPrice() * (double) item.getQuantity();
        }
        orderTotal = Math.round(orderTotal * 100.0) / 100.0;
        orderTotalTextView.setText(String.format("%.2f", orderTotal));
    }

    private void addCartListener(){
        ref = FirebaseDatabase.getInstance().getReference("Users/Customers/" + UID + "/cart");
        ref.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.e("Database", error.toException().toString());
    }

    public void updateDatabase(ArrayList<Item> data){
        this.data = data;
        ref = FirebaseDatabase.getInstance().getReference("Users/Customers/"+UID+"/cart");
        ref.removeValue();
        int itemIndex = 0;
        for (Item item : data){
            ref.child("item"+itemIndex).setValue(item);
            itemIndex++;
        }
    }

    public void placeOrder(ArrayList<Item> cart, StoreOwner owner, String UID) {

        Log.i("order", "owner name:" + owner.getName());
        Order order = createOrder(cart, owner);

        int orderNumber;
        Log.i("order", "placing order" + order);
        ref = FirebaseDatabase.getInstance().getReference("Users/Owners");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    if (child.child("name").getValue().toString().equals(owner.getName())){
                        Log.i("order", "found owner store");
                        int orderNumber = (int) child.child("orders").getChildrenCount();
                        String OID = child.getKey().toString();
                        addOrder(orderNumber, order, OID, UID);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void addOrder(int orderNumber, Order order, String OID, String UID) {
        Log.i("order", "submitting order");
        ref = FirebaseDatabase.getInstance().getReference("Users/Owners/"+OID);
        ref.child("/orders/order"+orderNumber).setValue(order);
        ref = FirebaseDatabase.getInstance().getReference("Users/Customers/"+UID);
        ref.child("orders/order"+orderNumber).setValue(order);

        ref.child("cart").removeValue();
        data.clear();
    }

    private Order createOrder(ArrayList<Item> cart, StoreOwner owner) {
        Order order = new Order(owner.getName(), cart);
        Log.i("order", order.toString());
        return order;
    }
}
