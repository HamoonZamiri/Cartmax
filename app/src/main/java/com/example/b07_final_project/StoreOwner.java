package com.example.b07_final_project;

import java.util.ArrayList;

public class StoreOwner extends User {
    private ArrayList<Item> products;
    private ArrayList<Order> orders;

    public StoreOwner() {
    }

    public StoreOwner(String email, String password, String name) {
        super(email,password,name);
        products = new ArrayList<Item>();
        orders = new ArrayList<Order>();
    }

    public void setProducts(ArrayList<Item> arrListItem){
        this.products = arrListItem;
    }

    public void setOrders(ArrayList<Order> arrListOrder){
        this.orders = arrListOrder;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<Item> getProducts() {
        return products;
    }
}
