package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerAddItemActivity extends AppCompatActivity {

    private Button addItemBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_item);

        addItemBtn = (Button) findViewById(R.id.commit_add_item);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference itemsRef = database.getReference("Users").child("Owners").child(currentUser.getUid()).child("store").child("products");
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (v.getId()) {
                    case R.id.commit_add_item:
                        addItem(itemsRef);
                }

            }
        });
    }
    private void addItem(DatabaseReference itemsRef) {
        try {
            final EditText itemName = (EditText) findViewById(R.id.editTextName);
            final EditText itemBrand = (EditText) findViewById(R.id.editTextBrand);
            final EditText itemDescription = (EditText) findViewById(R.id.editTextDescription);
            final EditText itemQty = (EditText) findViewById(R.id.editItemQuantity);
            final EditText itemPrice = (EditText) findViewById(R.id.editItemPrice);

            String nameString = itemName.getText().toString();
            String brandString = itemBrand.getText().toString();
            String descriptionString = itemDescription.getText().toString();
            int quantityInt = Integer.parseInt(itemQty.getText().toString());
            int priceInt = Integer.parseInt(itemPrice.getText().toString());

            if (nameString == "" || brandString == "" || descriptionString == "") {
                Toast.makeText(this, "Field can not be left empty", Toast.LENGTH_LONG).show();
            }

            Item newItem = new Item(nameString, brandString, priceInt, descriptionString);
            itemsRef.child(String.valueOf(newItem.hashCode())).child("name").setValue(nameString);
            itemsRef.child(String.valueOf(newItem.hashCode())).child("itemBrand").setValue(brandString);
            itemsRef.child(String.valueOf(newItem.hashCode())).child("itemDescription").setValue(descriptionString);
            itemsRef.child(String.valueOf(newItem.hashCode())).child("itemPrice").setValue(priceInt);
            itemsRef.child(String.valueOf(newItem.hashCode())).child("itemQty").setValue(quantityInt);

            startActivity(new Intent(this, OwnerListItemsActivity.class));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Field can not be left empty", Toast.LENGTH_SHORT).show();
        }
    }
}