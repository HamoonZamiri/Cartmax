package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private User user; // activity needs to be given a user
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        user = new User("email.com", "hunter2", "user1");
        if (user != null) {
            Database userDatabase = new Database(user);
            cartItems = userDatabase.getCartItems();
            Log.i("in here", "doing stuff");
        }
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));
        cartItems.add(new CartItem("Milk 4L", "Dairy Place", 4.69, "milk_4l", 1));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(cartItems);
        cartRecyclerView.setAdapter(adapter);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}