package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 20/03/2018.
 */

public class Courier {
    private int image;
    private String name;
    public Courier(int image, String name){
        this.name = name;
        this.image = image;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
