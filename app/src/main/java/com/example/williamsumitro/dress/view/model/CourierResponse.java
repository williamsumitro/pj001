package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 17/05/2018.
 */

public class CourierResponse {
    @SerializedName("courier")
    @Expose
    private ArrayList<CourierDetails> courier = null;

    public ArrayList<CourierDetails> getCourier() {
        return courier;
    }

    public void setCourier(ArrayList<CourierDetails> courier) {
        this.courier = courier;
    }
}
