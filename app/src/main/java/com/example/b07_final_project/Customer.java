package com.example.b07_final_project;

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Order> orders;

    public Customer() {
    }

    public Customer(String email, String password, String name) {
        super(email,password,name);
        orders = new ArrayList<Order>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
