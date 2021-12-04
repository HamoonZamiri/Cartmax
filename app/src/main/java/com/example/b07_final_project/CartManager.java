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

public class CartManager implements ValueEventListener{
    private ArrayList<CartItem> data;
    private DatabaseReference databaseRef;
    private CartAdapter adapter;
    private RecyclerView recyclerView;
    private Activity activity;
    private User user;

    public CartManager(RecyclerView view, User user, CartActivity activity){
        this.activity = activity;
        this.user = user;
        this.recyclerView = view;
        this.data = new ArrayList<CartItem>();
        this.adapter = new CartAdapter(data, activity);
        recyclerView.setAdapter(adapter);
        this.databaseRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        databaseRef.addListenerForSingleValueEvent(this);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ArrayList<CartItem> getData(){
        return data;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot child : snapshot.getChildren()){

            String temp = child.child("email").getValue().toString();
            Log.i("UID", "here");
            if (temp.equals(user.getEmail())){
                Log.i("UID", child.getKey().toString());
                snapshot = child.child("cart");
            }
        }
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

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.e("Database", error.toException().toString());
    }

    public DatabaseReference getDatabaseRef(){
        return databaseRef;
    }

    private String getUID(){
        return "TODO";
    }

    public void updateDatabase(String item, Object value){

    }
}
