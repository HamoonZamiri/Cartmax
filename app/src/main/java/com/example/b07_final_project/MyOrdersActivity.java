package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {
    private RecyclerView ordersRV;
    private OrdersListAdapter adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        getSupportActionBar().setTitle("Orders");

        TextView text = (TextView) findViewById(R.id.yourOrders);
        user = new User();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user.setName(extras.getString("userName"));
            user.setEmail(extras.getString("userEmail"));
        }

        DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        ArrayList<Order> orders = new ArrayList<Order>();
        String finalEmail = user.getEmail();
        customersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    for(DataSnapshot customer : task.getResult().getChildren()) {
                        if(customer.child("email").getValue(String.class).equals(finalEmail)) {
                            DataSnapshot ordersRef = customer.child("orders");
                            for(DataSnapshot order : ordersRef.getChildren()) {
                                Order o = parseOrder(order);
                                orders.add(o);
                            }
                        }
                    }
                }
                if(!orders.isEmpty()) {
                    text.setText("Your Orders");
                    setRecyclerView(orders);
                } else {
                    text.setText("You have no orders");
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

    public void setRecyclerView(ArrayList<Order> orders) {
        ordersRV = findViewById(R.id.ordersRV);
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> statuses = new ArrayList<String>();
        ArrayList<ArrayList<Item>> items = new ArrayList<ArrayList<Item>>();
        for(Order o : orders) {
            names.add(o.getStoreName());
            items.add(o.getItems());
            if(o.isComplete()) {
                statuses.add("Complete");
            } else {
                statuses.add("Processing");
            }
        }
        adapter = new OrdersListAdapter(this, names, statuses, items);
        ordersRV.setAdapter(adapter);
        ordersRV.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CustomerMainActivity.class);
        intent.putExtra("userName",user.getName());
        intent.putExtra("userEmail",user.getEmail());
        this.startActivity(intent);
    }
}
