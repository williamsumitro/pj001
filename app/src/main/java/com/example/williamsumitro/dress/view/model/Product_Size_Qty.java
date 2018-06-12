package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 10/06/2018.
 */

public class Product_Size_Qty {
    @SerializedName("size_id")
    @Expose
    private int size_id;
    @SerializedName("qty")
    @Expose
    private int qty;

    public Product_Size_Qty(int size_id, int qty){
        this.size_id = size_id;
        this.qty = qty;
    }
    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
