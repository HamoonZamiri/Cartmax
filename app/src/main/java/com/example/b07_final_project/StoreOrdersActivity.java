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
import android.provider.ContactsContract;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,StoreOrdersActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);


        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();


        //Initalize the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String uId = currentUser.getUid();

        //Read the database
        DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        ArrayList<Order> orders = new ArrayList<Order>();


        DatabaseReference itemsRef = database.getReference("Users").child("Owners").child(currentUser.getUid()).child("orders");
        itemsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot child: task.getResult().getChildren()) {
                        Order o = parseOrder(child);
                        orders.add(o);
                        System.out.println("Oncomplete");
                }

                int counter = 0;
                for (Order order : orders){
                    counter += 1;
                    ToDoModel todomodel = new ToDoModel();
                    todomodel.setId(counter);
                    todomodel.setTask(order.toString());
                    todomodel.setOrder(order);
                    if(order.isComplete())
                        todomodel.setStatus(1);
                    else
                        todomodel.setStatus(0);
                    taskList.add(todomodel);
                        //db.insertTask(todomodel);
                    System.out.println("order : orders");
                }

                System.out.println("Finished");
                Collections.reverse(taskList);

                tasksAdapter.setTasks(taskList);
            }
        });

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

        String brand = "";
        String description = "";
        String name = "";
        int price = 0;
        int quantity = 0;

        for(DataSnapshot data : order.child("products").getChildren()) {
        for(DataSnapshot data : order.child("items").getChildren()) {
            brand = data.child("brand").getValue(String.class);

            Item i = new Item(name,brand,price,description, quantity);
            items.add(i);
        }
        Order o = new Order(order.child("storeName").getValue(String.class), items);
        o.setComplete(Objects.requireNonNull(order.child("complete").getValue(Boolean.class)));
        return o;
    }

}