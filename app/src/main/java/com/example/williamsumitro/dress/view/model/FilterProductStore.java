package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 31/07/2018.
 */

public class FilterProductStore {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("product_info")
    @Expose
    private ArrayList<ProductInfo> productInfo = null;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("count_all")
    @Expose
    private Integer countAll;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }
}
