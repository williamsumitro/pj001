package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/25/2018.
 */

public class SortByIdStoreResult implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("product_info")
    @Expose
    private ArrayList<ProductInfo> productInfo = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ArrayList<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }
}
