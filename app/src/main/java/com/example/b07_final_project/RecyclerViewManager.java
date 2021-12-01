package com.example.b07_final_project;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewManager {
    private ArrayList<CartItem> data;
    private DatabaseReference databaseRef;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private Activity activity;

    public RecyclerViewManager(RecyclerView view, User user, Activity activity){
        this.activity = activity;
        recyclerView = view;
        data = new ArrayList<CartItem>();
        adapter = new RecyclerViewAdapter(data, activity);
        recyclerView.setAdapter(adapter);
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers").child(user.getName());
        databaseRef.child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateData(snapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("err", "listener failure");
            }
        });
    }

    private void updateData(DataSnapshot snapshot){
        data.clear();

        for (DataSnapshot child : snapshot.getChildren()){
            data.add(child.getValue(CartItem.class));
        }
        adapter.notifyDataSetChanged();
        TextView orderTotalTextView = activity.findViewById(R.id.orderTotalValue);
        Double orderTotal = 0.0;
        for (CartItem item : data) {
            orderTotal += item.getPrice() * (double) item.count;
        }
        orderTotalTextView.setText(orderTotal.toString());
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ArrayList<CartItem> getData(){
        return data;
    }
}
