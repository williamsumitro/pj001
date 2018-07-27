package com.example.williamsumitro.dress.view.model.dress_attribute;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Size implements Serializable{
    @SerializedName("size_id")
    @Expose
    private Integer sizeId;
    @SerializedName("size_name")
    @Expose
    private String sizeName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    private Integer qty;

    public Size(Integer sizeId, String sizeName, Integer qty){
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.qty = qty;
    }
    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
