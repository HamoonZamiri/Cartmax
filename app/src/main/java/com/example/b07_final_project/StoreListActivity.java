package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class StoreListActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        getSupportActionBar().setTitle("Stores");

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user = new User(extras.getString("userEmail"), "", extras.getString("userName"));
        }

        HashSet<String> owners = new HashSet<String>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("Owners");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("name").getValue(String.class);
                    owners.add(name);
                }
                setRecyclerView(owners);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("test", "Failed to read value.", error.toException());
            }
        });
    }

    public void setRecyclerView(HashSet<String> owners) {
        String[] names = new String[owners.size()];
        owners.toArray(names);
        recyclerView = findViewById(R.id.rv);
        StoresAdapter adapter = new StoresAdapter(this, names);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CustomerMainActivity.class);
        intent.putExtra("userName", user.getName());
        intent.putExtra("userEmail", user.getEmail());
        this.startActivity(intent);
    }
}