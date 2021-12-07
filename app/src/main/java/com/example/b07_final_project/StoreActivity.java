package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    private StoreItemsAdapter adapter;
    private StoreDataManager storeManager;
    private StoreOwner owner;
    private UserCartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        getSupportActionBar().setTitle("Checkout");

        Bundle extras = getIntent().getExtras();
        User user = new User();
        if(extras != null) {
            owner = new StoreOwner("", "", extras.getString("ownerName"));
            user.setEmail(extras.getString("userEmail"));
            user.setName(extras.getString("userName"));
        }
        cartManager = new UserCartManager(user, new ArrayList<Item>());
        if (extras != null && extras.getBoolean("newOrder")){
            cartManager.newOrder(owner.getName());
        }
        cartManager.getCart();

        storeManager = new StoreDataManager(owner, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.storeItemsRecyclerView);
        adapter = new StoreItemsAdapter(storeManager.getData(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button checkoutButton = (Button) findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CartActivity.class);
                intent.putExtra("ownerName", owner.getName());
                intent.putExtra("userEmail", cartManager.getUser().getEmail());
                intent.putExtra("userName", cartManager.getUser().getName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        TextView storeName = findViewById(R.id.storePageName);
        storeName.setText(owner.getName());
    }

    @Override
    public void onPause(){
        super.onPause();
        cartManager.updateDatabase(adapter.getCart());
        adapter.getCart().clear();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, CustomerMainActivity.class);
        intent.putExtra("userEmail", cartManager.getUser().getEmail());
        intent.putExtra("userName", cartManager.getUser().getName());
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        cartManager.getUserCart();
    }

    public StoreItemsAdapter getAdapter() { return adapter;
    }
}