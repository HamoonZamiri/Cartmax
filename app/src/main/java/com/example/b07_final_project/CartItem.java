package com.example.b07_final_project;

public class CartItem extends Item{
    private int count;

    public CartItem(String name, String brand, double price, String description) {
        super(name, brand, price, description);
        count = 1;
    }

    public CartItem(String name, String brand, double price, String description, int count) {
        super(name, brand, price, description);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public String toString(){
        return super.toString() + " x " + count;
    }
}
