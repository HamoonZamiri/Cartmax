package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        getSupportActionBar().setTitle("Stores");

        HashSet<String> owners = new HashSet<String>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("Owners");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("name").getValue(String.class);
                    owners.add(name);
                    Log.i("test", name);
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
}