package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Price {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("qty_min")
    @Expose
    private String qtyMin;
    @SerializedName("qty_max")
    @Expose
    private String qtyMax;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getQtyMin() {
        return qtyMin;
    }

    public void setQtyMin(String qtyMin) {
        this.qtyMin = qtyMin;
    }

    public String getQtyMax() {
        return qtyMax;
    }

    public void setQtyMax(String qtyMax) {
        this.qtyMax = qtyMax;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
