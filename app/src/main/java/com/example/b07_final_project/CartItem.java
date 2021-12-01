package com.example.b07_final_project;

public class CartItem extends Item{
    public int count;

    public CartItem(String name, String brand, double price, String image) {
        super(name, brand, price, image);
        count = 1;
    }

    public CartItem(String name, String brand, double price, String image, int count) {
        super(name, brand, price, image);
        this.count = count;
    }
}
