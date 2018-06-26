package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class Purchase_OrderResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<Purchase_OrderResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Purchase_OrderResult> getPurchase_OrderResult() {
        return result;
    }

    public void setPurchase_OrderResult(ArrayList<Purchase_OrderResult> result) {
        this.result = result;
    }
}
