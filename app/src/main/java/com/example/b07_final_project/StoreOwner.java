package com.example.b07_final_project;

import java.util.ArrayList;

public class StoreOwner extends User {
    private ArrayList<Item> products;
    private ArrayList<ArrayList<Item>> orders;

    public StoreOwner() {
    }

    public StoreOwner(String username, String password, String name) {
        super(username,password,name);
        products = new ArrayList<Item>();
        orders = new ArrayList<ArrayList<Item>>();
    }

    public ArrayList<ArrayList<Item>> getOrders() {
        return orders;
    }

    public ArrayList<Item> getProducts() {
        return products;
    }
}
