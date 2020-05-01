package com.example.splashscreenwithlogin;

public class selectOrder {
    private String order_id;
    private String price;
    private String loc_lat;
    private String loc_lang;
    private String loc_desc;
    private String time;

    public selectOrder(String order_id, String price, String loc_lat, String loc_lang, String loc_desc, String time) {
        this.order_id = order_id;
        this.price = price;
        this.loc_lat = loc_lat;
        this.loc_lang = loc_lang;
        this.loc_desc = loc_desc;
        this.time = time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLoc_lat() {
        return loc_lat;
    }

    public void setLoc_lat(String loc_lat) {
        this.loc_lat = loc_lat;
    }

    public String getLoc_lang() {
        return loc_lang;
    }

    public void setLoc_lang(String loc_lang) {
        this.loc_lang = loc_lang;
    }

    public String getLoc_desc() {
        return loc_desc;
    }

    public void setLoc_desc(String loc_desc) {
        this.loc_desc = loc_desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
