package com.example.splashscreenwithlogin;

public class Cart {
    private int id;
    private String title;
    private String image;
    private int quantity;
    private int price;
    private int original_pizza_price;


    public Cart(int id, String title, int quantity, int price, String image, int original_pizza_price) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.original_pizza_price = original_pizza_price;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getOriginal_pizza_price() {
        return original_pizza_price;
    }
}