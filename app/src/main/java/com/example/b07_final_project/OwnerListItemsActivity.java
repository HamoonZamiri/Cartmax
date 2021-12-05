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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OwnerListItemsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_list_items);

        // Button to add to list

        Button newItem = (Button) findViewById(R.id.add_item);
        newItem.setOnClickListener(this);

        // Listing items from database

        List<String> lst_names = new ArrayList<String>();
        List<String> lst_brands = new ArrayList<String>();
        List<String> lst_descriptions = new ArrayList<String>();
        List<Integer> lst_quantities = new ArrayList<Integer>();
        List<Integer> lst_prices = new ArrayList<Integer>();

        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String uId = currentUser.getUid();


        DatabaseReference itemsRef = database.getReference("Users").child("Owners").child(uId).child("store").child("products");
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String name = child.child("name").getValue(String.class);
                    System.out.println(name);
                    lst_names.add(name);
                    String brand = child.child("brand").getValue(String.class);
                    lst_brands.add(brand);
                    String description = child.child("description").getValue(String.class);
                    lst_descriptions.add(description);
                    int quantity = Integer.parseInt(Objects.requireNonNull(child.child("quantity").getValue(String.class)));
                    lst_quantities.add(quantity);
                    int price = Integer.parseInt(Objects.requireNonNull(child.child("price").getValue(String.class)));
                    lst_prices.add(price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                throw error.toException();
            }
        });

        recyclerView = findViewById(R.id.viewItems);
        OwnerListItemsAdapter adapter = new OwnerListItemsAdapter(this, lst_names, lst_brands, lst_descriptions, lst_quantities, lst_prices);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_item:
                startActivity(new Intent(this, OwnerAddItemActivity.class));
                break;
        }
    }
}