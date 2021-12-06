package com.example.b07_final_project;

public class Item {
    private String name;
    private String brand;
    private double price;
    private String description;
    private int quantity;

    public Item() {
    }

    public Item(String name, String brand, double price, String description, int quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description; // added new description field for item object
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {this.price = price;}

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {this.brand = brand;}

    public String getDescription() { return description; }

    public void setDescription(String description){ this.description = description;}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return name + "[" + brand + "]: $" + price;
    }
}
