package com.example.b07_final_project;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreDataManager extends DataManager<Item> implements ValueEventListener{

    public StoreDataManager(User user, Activity activity){
        this.activity = activity;
        this.data = new ArrayList<Item>();

        // single use event listener to find which owner's store to access
        this.ref = FirebaseDatabase.getInstance().getReference("Users").child("Owners");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    String temp = child.child("name").getValue().toString();
                    if (temp.equals(user.getName())){
                        UID = child.getKey().toString();
                        addStoreListener();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database error", error.toException().toString());
            }
        });
    }

    private void addStoreListener() {
        ref = FirebaseDatabase.getInstance().getReference("Users/Owners/" + UID + "/products");
        ref.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        data.clear();
        for (DataSnapshot child : snapshot.getChildren()){
            data.add(child.getValue(Item.class));
        }
        StoreActivity activity = (StoreActivity) this.activity;
        activity.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.e("Database", error.toException().toString());
    }

    @Override
    public void updateDatabase(ArrayList<Item> data) {

    }

    @Override
    public ArrayList<Item> getData() {
        return data;
    }
}
