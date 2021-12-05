package com.example.b07_final_project;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartDataManager implements ValueEventListener{
    // manages data inside of cart recyclerView by reading and writing to firebase db
    private ArrayList<CartItem> data;
    private DatabaseReference databaseRef;
    private CartActivity activity;
    private String UID;

    public CartDataManager(User user, CartActivity activity){
        this.activity = activity;
        this.data = new ArrayList<CartItem>();

        // single use event listener to find which user's cart to access
        this.databaseRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public ArrayList<CartItem> getData(){
        return data;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        data.clear();

        for (DataSnapshot child : snapshot.getChildren()){
            data.add(child.getValue(CartItem.class));
        }
        activity.updateAdapter();
        TextView orderTotalTextView = activity.findViewById(R.id.orderTotalValue);
        Double orderTotal = 0.0;
        for (CartItem item : data) {
            orderTotal += item.getPrice() * (double) item.count;
        }
        orderTotal = Math.round(orderTotal * 100.0) / 100.0;
        orderTotalTextView.setText(String.format("%.2f", orderTotal));
    }

    private void addCartListener(){
        databaseRef = FirebaseDatabase.getInstance().getReference("Users/Customers/" + UID + "/cart");
        databaseRef.addListenerForSingleValueEvent(this);
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

    public void updateDatabase(int deleted){

        databaseRef = FirebaseDatabase.getInstance().getReference("Users/Customers/"+UID+"/cart");
        for (int i = 0; i < data.size(); i++){
            databaseRef.child("item"+i).setValue(data.get(i));
            Log.i("database update", "setting count to "  + data.get(i).count);
        }

        for (int i = data.size(); i < data.size() + deleted; i++){
            Log.i("database update", "removing item");
            databaseRef.child("item"+i).removeValue();
        }

    }
}
