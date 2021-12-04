package com.example.b07_final_project.Model;

import com.example.b07_final_project.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToDoModel {
    private int id, status;
    private Order order;
    private String task;


    public void setOrder(Order order) { this.order = order; }

    public Order getOrder() { return order; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
