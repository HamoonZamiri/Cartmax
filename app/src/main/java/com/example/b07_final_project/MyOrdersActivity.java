package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        ArrayList<Order> orders = new ArrayList<Order>();
        String testEmail = "xyz@gmail.com";

        customersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    for(DataSnapshot customer : task.getResult().getChildren()) {
                        if(customer.child("email").getValue(String.class).equals(testEmail)) {
                            DataSnapshot ordersRef = customer.child("orders");
                            for(DataSnapshot order : ordersRef.getChildren()) {
                                Order o = parseOrder(order);
                                orders.add(o);
                            }
                        }
                    }
                }
                for(Order o : orders) {
                    for(Item i : o.getItems())
                        Log.i("test", o.getStoreName() + ": " + i.toString());
                }
            }
        });


    }

    public Order parseOrder(DataSnapshot order) {
        ArrayList<Item> items = new ArrayList<Item>();
        for(DataSnapshot data : order.child("items").getChildren()) {
            Item i = data.getValue(Item.class);
            items.add(i);
        }
        Order o = new Order(order.child("storeName").getValue(String.class), items);
        o.setComplete(order.child("complete").getValue(Boolean.class));
        return o;
    }
}
