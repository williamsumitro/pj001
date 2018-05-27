package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 17/05/2018.
 */

public class CourierResponse {
    @SerializedName("courier")
    @Expose
    private List<CourierDetails> courier = null;

    public List<CourierDetails> getCourier() {
        return courier;
    }

    public void setCourier(List<CourierDetails> courier) {
        this.courier = courier;
    }
}
