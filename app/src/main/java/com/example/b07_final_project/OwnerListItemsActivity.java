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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        ArrayList<String> lst_names = new ArrayList<String>();
        ArrayList<String> lst_brands = new ArrayList<String>();
        ArrayList<String> lst_descriptions = new ArrayList<String>();
        ArrayList<Integer> lst_quantities = new ArrayList<Integer>();
        ArrayList<Double> lst_prices = new ArrayList<Double>();

        // Read from the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String uId = currentUser.getUid();

        // Setting text to user full name

        TextView tv = (TextView) findViewById(R.id.customerName);
        DatabaseReference nameRef = database.getReference("Users").child("Owners").child(uId).child("name");
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                tv.setText(username);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        DatabaseReference itemsRef = database.getReference("Users").child("Owners").child(uId).child("store").child("products");
        itemsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                for (DataSnapshot child: task.getResult().getChildren()) {
                    String name = Objects.requireNonNull(child.child("name").getValue(String.class));
                    lst_names.add(name);
                    //String brand = Objects.requireNonNull(child.child("itemBrand").getValue(String.class));
                    String brand = Objects.requireNonNull(child.child("brand").getValue(String.class));
                    lst_brands.add(brand);
                    //String description = Objects.requireNonNull(child.child("itemDescription").getValue(String.class));
                    String description = Objects.requireNonNull(child.child("description").getValue(String.class));
                    lst_descriptions.add(description);
                    //Integer quantity = Objects.requireNonNull(child.child("itemQty").getValue(Integer.class));
                    Integer quantity = Objects.requireNonNull(child.child("quantity").getValue(Integer.class));
                    lst_quantities.add(quantity);
                    //Integer price = Objects.requireNonNull(child.child("itemPrice").getValue(Integer.class));
                    Double price = Objects.requireNonNull(child.child("price").getValue(Double.class));
                    lst_prices.add(price);
                }
                setRecyclerView(lst_names, lst_brands, lst_descriptions, lst_quantities, lst_prices);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_item:
                startActivity(new Intent(this, OwnerAddItemActivity.class));
                break;
        }
    }

    public void setRecyclerView(ArrayList<String> lst_names, ArrayList<String> lst_brands,
                                ArrayList<String> lst_descriptions, ArrayList<Integer> lst_quantities,
                                ArrayList<Double> lst_prices) {

        String[] nameArray = lst_names.toArray(new String[0]);
        String[] brandArray = lst_brands.toArray(new String[0]);
        String[] descriptionArray = lst_descriptions.toArray(new String[0]);
        Double[] priceArray = lst_prices.toArray(new Double[0]);
        Integer[] quantityArray = lst_quantities.toArray(new Integer[0]);
        recyclerView = findViewById(R.id.viewItems);
        OwnerListItemsAdapter adapter = new OwnerListItemsAdapter(this, nameArray, brandArray,
                descriptionArray, quantityArray, priceArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}