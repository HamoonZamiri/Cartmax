package com.example.b07_final_project;

public class Item {
    private String name;
    private String brand;
    private int price;
    private String description;

    public Item() {
    }

    public Item(String name, String brand, int price, String description) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description; // added new description field for item object
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() { return description; }

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
        return name.hashCode() + brand.hashCode() + price;
    }

    @Override
    public String toString() {
        return name + "[" + brand + "]: $" + price;
    }
}
