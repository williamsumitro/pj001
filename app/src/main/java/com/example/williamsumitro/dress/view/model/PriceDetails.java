package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 24/03/2018.
 */

public class PriceDetails {
    private int qty, discount;
    public PriceDetails(int qty, int discount){
        this.qty = qty;
        this.discount = discount;
    }
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
