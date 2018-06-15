package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class CheckoutResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<CheckoutInfo> result = null;
    @SerializedName("total_qty")
    @Expose
    private String totalQty;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("available_points")
    @Expose
    private Integer availablePoints;
    @SerializedName("message")
    @Expose
    private String message;
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<CheckoutInfo> getCheckoutInfo() {
        return result;
    }

    public void setCheckoutInfo(ArrayList<CheckoutInfo> result) {
        this.result = result;
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

    public Integer getAvailablePoints() {
        return availablePoints;
    }

    public void setAvailablePoints(Integer availablePoints) {
        this.availablePoints = availablePoints;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
