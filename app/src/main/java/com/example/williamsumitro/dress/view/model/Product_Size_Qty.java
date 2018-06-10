package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 10/06/2018.
 */

public class Product_Size_Qty {
    @SerializedName("size_id")
    @Expose
    private String size_id;
    @SerializedName("qty")
    @Expose
    private String qty;

    public String getSize_id() {
        return size_id;
    }

    public void setSize_id(String size_id) {
        this.size_id = size_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
