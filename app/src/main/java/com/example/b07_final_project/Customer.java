package com.example.b07_final_project;

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Item> order;

    public Customer(String username, String password, String name) {
        super(username,password,name);
        order = new ArrayList<Item>();
    }

    public void addItem(Item i) {
        order.add(i);
    }

    public ArrayList<Item> getOrder() {
        return order;
    }
}
