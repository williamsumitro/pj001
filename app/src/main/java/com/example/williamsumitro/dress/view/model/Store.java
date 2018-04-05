package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 01/04/2018.
 */

public class Store {
    private int image;
    private double rate;
    private String name;
    public Store(String name, int image, double rate){
        this.image = image;
        this.name = name;
        this.rate = rate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
