package com.example.b07_final_project;

public class CartItem extends Item{
    public int count;

    public CartItem(){}

    public CartItem(String name, String brand, double price) {
        super(name, brand, price);
        count = 1;
    }

    public CartItem(String name, String brand, double price, int count) {
        super(name, brand, price);
        this.count = count;
    }

    @Override
    public String toString(){
        return super.toString() + " x " + count;
    }
}
