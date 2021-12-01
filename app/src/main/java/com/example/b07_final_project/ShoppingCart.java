package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    private RecyclerViewManager cartRecyclerViewManager;
    private User user; // activity needs to be given a user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        user = new User("email.com", "hunter2", "user1");

        RecyclerView view = (RecyclerView) findViewById(R.id.cartRecyclerView);
        cartRecyclerViewManager = new RecyclerViewManager(view, user, this);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdate();
            }
        });
        Double orderTotal = 0.0;
        for (CartItem item : cartRecyclerViewManager.getData()) {
            orderTotal += item.getPrice() * (double) item.count;
        }
        TextView orderTotalTextView = (TextView) findViewById(R.id.orderTotalValue);
        orderTotalTextView.setText(orderTotal.toString());
    }

    protected void onUpdate(){
        TextView orderTotalTextView = (TextView) findViewById(R.id.orderTotalValue);
        Double orderTotal = 0.0;
        for (CartItem item : cartRecyclerViewManager.getData()) {
            orderTotal += item.getPrice() * (double) item.count;
        }
        orderTotalTextView.setText(orderTotal.toString());
    }
}