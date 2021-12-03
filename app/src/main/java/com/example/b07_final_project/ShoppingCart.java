package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ShoppingCart extends AppCompatActivity {

    private CartManager cartRecyclerViewManager;
    private User user; // activity needs to be given a user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        user = new User("email.com", "hunter2", "user1");

        RecyclerView view = (RecyclerView) findViewById(R.id.cartRecyclerView);
        cartRecyclerViewManager = new CartManager(view, user, this);
        view.setLayoutManager(new LinearLayoutManager(this));
    }

}