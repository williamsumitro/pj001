package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 11/06/2018.
 */

public class BagResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("bag")
    @Expose
    private ArrayList<Bag> bag = null;
    @SerializedName("total_qty")
    @Expose
    private String totalQty;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Bag> getBagDetail() {
        return bag;
    }

    public void setBagDetail(ArrayList<Bag> bag) {
        this.bag = bag;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
