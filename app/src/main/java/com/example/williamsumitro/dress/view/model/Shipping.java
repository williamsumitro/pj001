package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 30/03/2018.
 */

public class Shipping {
    private String name;
    private int price;
    public Shipping(String name, int price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
