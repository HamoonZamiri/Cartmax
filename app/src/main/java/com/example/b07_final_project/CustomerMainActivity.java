package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomerMainActivity extends AppCompatActivity implements View.OnClickListener {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        email = "";
        String name = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            email = extras.getString("email");
            name = extras.getString("name");
        }

        TextView nameText = (TextView) findViewById(R.id.customerName);
        nameText.setText(name);

        Button newOrder = (Button) findViewById(R.id.newOrder);
        newOrder.setOnClickListener(this);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);

        Button myOrders = (Button) findViewById(R.id.myOrders);
        myOrders.setOnClickListener(this);

        Button startCart = (Button) findViewById(R.id.startCart);
        startCart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newOrder:
                startActivity(new Intent(this, StoreListActivity.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.myOrders:
                Intent i = new Intent(this, MyOrdersActivity.class);
                i.putExtra("email",email);
                startActivity(i);
                break;
            case R.id.startCart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                cartIntent.putExtra("email",email);
                startActivity(cartIntent);
        }
    }

    @Override
    public void onBackPressed() {
    }

}