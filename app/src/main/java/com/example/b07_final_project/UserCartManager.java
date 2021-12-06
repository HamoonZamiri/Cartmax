package com.example.b07_final_project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserCartManager extends DataManager<Item> {
    User user;

    public UserCartManager(User user, ArrayList<Item> cartItems){
        this.user = user;
        this.data = cartItems;

        getUserCart();
    }

    @Override
    public void updateDatabase(ArrayList<Item> newItems) {
        for (Item item : this.data){
            if (newItems.contains(item)){
                item.setQuantity(item.getQuantity() + newItems.get(newItems.indexOf(item)).getQuantity());
                newItems.remove(item);
            }
        }
        ref = FirebaseDatabase.getInstance().getReference("Users/Customers/"+UID+"/cart");
        this.data.addAll(newItems);
        int itemIndex = 0;
        for (Item item : data){
            ref.child("item"+itemIndex).setValue(item);
            itemIndex++;
        }
    }


    @Override
    public ArrayList<Item> getData() {
        return this.data;
    }

    public void getUserCart() {
        // single use event listener to find which user's cart to access
        ref = FirebaseDatabase.getInstance().getReference("Users/Customers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot child : snapshot.getChildren()){
                    String temp = child.child("email").getValue().toString();
                    if (temp.equals(user.getEmail())){
                        UID = child.getKey();
                        Log.i("UID", UID);
                        for (DataSnapshot item : child.child("cart").getChildren()){
                            data.add(item.getValue(Item.class));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database error", error.toException().toString());
            }
        });
    }

    public User getUser(){
        return user;
    }

    public void newOrder(String storeName) {
        ref = FirebaseDatabase.getInstance().getReference("Users/Customers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot users : snapshot.getChildren()){
                    if (users.child("email").getValue().toString().equals(user.getEmail())){
                        UID = users.getKey();
                        resetCart(storeName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void resetCart(String storeName){
        ref = FirebaseDatabase.getInstance().getReference("Users/Customers/"+UID);
        ref.child("cart").removeValue();
        ref.child("store").setValue(storeName);
        data.clear();
    }
}
