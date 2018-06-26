package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class ApproveOrderProduct {

    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("status")
    @Expose
    private String status;

    public ApproveOrderProduct(String product_id, String status){
        this.product_id = product_id;
        this.status = status;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
