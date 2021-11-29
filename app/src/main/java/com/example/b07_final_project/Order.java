package com.example.b07_final_project;

import java.util.ArrayList;

public class Order {
    private String storeName;
    private ArrayList<Item> items;
    private boolean complete;

    public Order() {
    }

    public Order(String storeName, ArrayList<Item> items) {
        this.storeName = storeName;
        this.items = items;
        complete = false;
    }

    public void addItem(Item i) {
        items.add(i);
    }

    public int getQuantity(Item i) {
        int count = 0;
        for(Item item : items) {
            if(item.equals(i)) {
                count++;
            }
        }
        return count;
    }

    public String getStoreName() {
        return storeName;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean val) {
        complete = val;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

}
