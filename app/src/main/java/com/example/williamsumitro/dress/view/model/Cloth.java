package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 02/03/2018.
 */

public class Cloth {
    private String id, name, price;
    private int picture;
    public Cloth(){

    }
    public Cloth(String name, int picture, String price){
        this.name = name;
        this.picture = picture;
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
