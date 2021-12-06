package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.b07_final_project.Utils.DatabaseHandler;
import com.example.b07_final_project.Model.ToDoModel;
import com.example.b07_final_project.Adapters.ToDoAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class StoreOrdersActivity extends AppCompatActivity implements DialogCloseListener{

    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        getSupportActionBar().setTitle("Store Orders");

        db = new DatabaseHandler(this);
        db.openDatabase();

        /*
        String id = "user3";
        Item item = new Item("macbook", "apple", 1000, "apple laptop");
        ArrayList<Item> arrListItem = new ArrayList<Item>();
        arrListItem.add(item);
        Order order = new Order("Apple store", arrListItem);
        DatabaseReference fdb = FirebaseDatabase.getInstance().getReference();
        ArrayList<Order> arrListOrder = new ArrayList<Order>();
        arrListOrder.add(order);
        StoreOwner storeOwner = new StoreOwner("lol", "lol", "lol");
        storeOwner.setOrders(arrListOrder);
        storeOwner.setProducts(arrListItem);
        fdb.child("Users").child("Owners").child("user3").child("store").setValue(storeOwner); //placeholder */

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,StoreOrdersActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}