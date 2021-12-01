package com.example.b07_final_project;

public class Item {
    private String name;
    private String brand;
    private double price;

    public Item(){}

    public Item(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String toString(){
        return brand + " " + name + ", " + price;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        Item other = (Item)obj;
        return this.name.equals(other.name) && this.brand.equals(other.brand) && this.price == other.price;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + brand.hashCode() + (int) price;
    }

}