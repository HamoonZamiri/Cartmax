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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.b07_final_project.Utils.DatabaseHandler;
import com.example.b07_final_project.Model.ToDoModel;
import com.example.b07_final_project.Adapters.ToDoAdapter;
import com.google.firebase.database.DataSnapshot;
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
    private String email;

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
        fdb.child("Users").child("Owners").child("user3").child("store").setValue(storeOwner); //placeholder test*/

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,StoreOrdersActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();

        //Read the database
        DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        ArrayList<Order> orders = new ArrayList<Order>();

        email = "";
        String name = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            email = extras.getString("email");
            name = extras.getString("name");
        }


        //email = "hoomji@mail.com"; //placeholder test

        String finalEmail = email; //add existing orders if any
        customersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    for(DataSnapshot customer : task.getResult().getChildren()) {
                        if(customer.child("email").getValue(String.class).equals(finalEmail)) {
                            DataSnapshot ordersRef = customer.child("orders");
                            for(DataSnapshot order : ordersRef.getChildren()) {
                                Order o = parseOrder(order);
                                orders.add(o);
                            }
                        }
                    }
                }
            }
        });

        int counter = 0;
        for (Order order : orders){
            counter += 1;
            ToDoModel todomodel = new ToDoModel();
            todomodel.setId(counter);
            todomodel.setTask(order.toString());
            todomodel.setStatus(0);
            taskList.add(todomodel);
            System.out.println("Test");
        }
        System.out.println("Orders");
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

    public Order parseOrder(DataSnapshot order) {
        ArrayList<Item> items = new ArrayList<Item>();
        for(DataSnapshot data : order.child("items").getChildren()) {
            Item i = data.getValue(Item.class);
            items.add(i);
        }
        Order o = new Order(order.child("storeName").getValue(String.class), items);
        o.setComplete(order.child("complete").getValue(Boolean.class));
        return o;
    }

}