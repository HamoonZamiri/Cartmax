package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OwnerMainActivity extends AppCompatActivity implements View.OnClickListener {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);

        getSupportActionBar().setTitle("Owner");

        TextView name = (TextView) findViewById(R.id.customerName);
        Customer c = new Customer("lol@gmail.com", "kek", "LMAO"); //placeholder
        name.setText("First Last");

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);

        Button myStore = (Button) findViewById(R.id.myStore);
        myStore.setOnClickListener(this);

        Button button_store_orders = (Button) findViewById(R.id.button_store_orders);
        button_store_orders.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.myStore:
                startActivity(new Intent(this, OwnerListItemsActivity.class));
                break;

            case R.id.button_store_orders:
<<<<<<< HEAD
                startActivity(new Intent(this, StoreOrdersActivity.class));
=======
                Intent i = new Intent(this, StoreOrdersActivity.class);
                i.putExtra("email",email);
                startActivity(i);
>>>>>>> Kenny_owner
                break;
        }
    }


    @Override
    public void onBackPressed() {
    }

}