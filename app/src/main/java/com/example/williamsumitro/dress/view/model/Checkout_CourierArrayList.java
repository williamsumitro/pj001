package com.example.williamsumitro.dress.view.model;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 14/06/2018.
 */

public class Checkout_CourierArrayList {
    private ArrayList<Checkout_Courier> checkout_courierArrayList;

    public Checkout_CourierArrayList(ArrayList<Checkout_Courier> checkout_courierArrayList){
        this.checkout_courierArrayList = checkout_courierArrayList;
    }

    public ArrayList<Checkout_Courier> getCheckout_courierArrayList() {
        return checkout_courierArrayList;
    }

    public void setCheckout_courierArrayList(ArrayList<Checkout_Courier> checkout_courierArrayList) {
        this.checkout_courierArrayList = checkout_courierArrayList;
    }
}
