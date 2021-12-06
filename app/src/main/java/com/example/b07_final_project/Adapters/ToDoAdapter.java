package com.example.b07_final_project.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.AddNewTask;
import com.example.b07_final_project.Item;
import com.example.b07_final_project.Order;
import com.example.b07_final_project.StoreOrdersActivity;
import com.example.b07_final_project.Model.ToDoModel;
import com.example.b07_final_project.R;
import com.example.b07_final_project.Utils.DatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private DatabaseHandler db;
    private StoreOrdersActivity activity;

    public ToDoAdapter(DatabaseHandler db, StoreOrdersActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public List<ToDoModel> gettodoList(){
        return this.todoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                    setCompleteFirebase(item, true);
                } else {
                    db.updateStatus(item.getId(), 0);
                    setCompleteFirebase(item, false);
                }
            }
        });
    }

    public void setCompleteFirebase(ToDoModel item, boolean status){

    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        if(todoList!= null)
            return todoList.size();
        else
            return 0;
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());

        //Initalize the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String uId = currentUser.getUid();

        //Read the database
        DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference("Users").child("Customers");

        DatabaseReference itemsRef = database.getReference("Users").child("Owners").child(currentUser.getUid()).child("orders");
        itemsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot child: task.getResult().getChildren()) {
                    Order o = parseOrder(child);
                    if(o.toString().equals(item.getTask()))
                        child.child("complete").getRef().setValue(true);

                    System.out.println("Oncomplete");
                }

                todoList.remove(position);
                notifyItemRemoved(position);
            }
        });


    }

    public void editItem(int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }

    public Order parseOrder(DataSnapshot order) {
        ArrayList<Item> items = new ArrayList<Item>();

        String brand = "";
        String description = "";
        String name = "";
        int price = 0;

        for(DataSnapshot data : order.child("products").getChildren()) {
            //Item i = data.getValue(Item.class);
            //items.add(i);

            brand = data.child("itemBrand").getValue(String.class);
            description = data.child("itemDescription").getValue(String.class);
            name = data.child("itemName").getValue(String.class);
            price = data.child("itemPrice").getValue(int.class);

            Item i = new Item(name,brand,price,description);
            items.add(i);
        }
        Order o = new Order(order.child("storeName").getValue(String.class), items);
        o.setComplete(Objects.requireNonNull(order.child("complete").getValue(Boolean.class)));
        return o;
    }

}
