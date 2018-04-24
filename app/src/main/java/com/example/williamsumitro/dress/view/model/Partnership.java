package com.example.williamsumitro.dress.view.model;

/**
 * Created by William Sumitro on 19/04/2018.
 */

public class Partnership {
    private String store_name, location;
    private int store_image, price, min_order;
    public Partnership(String store_name, String location, int store_image, int price, int min_order){
        this.store_name = store_name;
        this.location = location;
        this.store_image = store_image;
        this.price = price;
        this.min_order = min_order;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStore_image() {
        return store_image;
    }

    public void setStore_image(int store_image) {
        this.store_image = store_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMin_order() {
        return min_order;
    }

    public void setMin_order(int min_order) {
        this.min_order = min_order;
    }
}
