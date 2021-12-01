package com.example.b07_final_project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Database {
    DatabaseReference ref;
    ValueEventListener listener;
    List<CartItem> cartItems;

    public Database(User user){
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customers").child(user.getName());
    }
    public List<CartItem> getCartItems(){
        ref.child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("extracting", "say something");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("err", "cancelled");
            }
        });
        Log.i("extrating", "done extracting");
        return cartItems;
    }
}
