package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{

    private CartDataManager cartDataManager;
    private CartAdapter adapter;
    private User user; // activity needs to be given a user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = new User(extras.getString("email"), "", "");
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.placeOrderButton:
                placeOrder();
                break;
        }
    }

    private void placeOrder(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    }

    public CartDataManager getCartRecyclerViewManager(){
        return cartDataManager;
    }

    public void updateAdapter(){
        adapter.notifyDataSetChanged();
    }
}