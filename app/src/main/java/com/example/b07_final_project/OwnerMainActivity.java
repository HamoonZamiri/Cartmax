package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerMainActivity extends AppCompatActivity implements View.OnClickListener {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);

        getSupportActionBar().setTitle("Owner");

        email = "";
        String name = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            email = extras.getString("email");
            name = extras.getString("name");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String uId = currentUser.getUid();

        // Setting text to user full name

        TextView tv = (TextView) findViewById(R.id.customerName);
        tv.setText(currentUser.getEmail());

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
                Intent i = new Intent(this, StoreOrdersActivity.class);
                i.putExtra("email",email);
                startActivity(i);
                break;
        }
    }


    @Override
    public void onBackPressed() {
    }

}