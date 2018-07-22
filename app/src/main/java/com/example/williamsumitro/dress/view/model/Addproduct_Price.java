package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 18/07/2018.
 */

public class Addproduct_Price {
    private String qty_min, qty_max, price;
    public Addproduct_Price(String qty_min, String qty_max, String price){
        this.qty_min = qty_min;
        this.qty_max = qty_max;
        this.price = price;
    }

    public String getQty_min() {
        return qty_min;
    }

    public void setQty_min(String qty_min) {
        this.qty_min = qty_min;
    }

    public String getQty_max() {
        return qty_max;
    }

    public void setQty_max(String qty_max) {
        this.qty_max = qty_max;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }
}
