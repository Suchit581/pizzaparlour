package com.example.splashscreenwithlogin;

public class PizzaType {
    private int id;
    private String title;
    private String shortdesc;
    private int price;
    private String image;

    public PizzaType(int id, String title, String shortdesc, int price, String image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}