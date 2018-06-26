package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class Sales_OrderResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<Sales_OrderResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Sales_OrderResult> getSales_OrderResult() {
        return result;
    }

    public void setSales_OrderResult(ArrayList<Sales_OrderResult> result) {
        this.result = result;
    }
}
