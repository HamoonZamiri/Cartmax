package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Owner;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{

    private CartDataManager cartDataManager;
    private CartAdapter adapter;
    private User user; // activity needs to be given a user
    private StoreOwner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        getSupportActionBar().setTitle("Your Cart");

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = new User(extras.getString("userEmail"), "", extras.getString("userName"));
            owner = new StoreOwner("", "", extras.getString("ownerName"));
        }
        cartDataManager = new CartDataManager(user, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        adapter = new CartAdapter(cartDataManager.getData(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart(){
        super.onStart();
        Button placeOrderButton = (Button) findViewById(R.id.placeOrderButton);
        placeOrderButton.setOnClickListener(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        cartDataManager.updateDatabase(adapter.getCart());
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.placeOrderButton:
                if (cartDataManager.data.size() == 0){
                    break;
                }
                cartDataManager.placeOrder(adapter.getCart(), owner, cartDataManager.UID);
                Intent intent = new Intent(this, MyOrdersActivity.class);
                intent.putExtra("userName", user.getName());
                intent.putExtra("userEmail", user.getEmail());
                this.startActivity(intent);
                break;
        }
    }

    public CartDataManager getCartRecyclerViewManager(){
        return cartDataManager;
    }

    public CartAdapter getAdapter() { return adapter;
    }
}