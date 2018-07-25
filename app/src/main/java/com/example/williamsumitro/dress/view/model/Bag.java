package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 11/06/2018.
 */

public class Bag {
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_photo")
    @Expose
    private String storePhoto;
    @SerializedName("product")
    @Expose
    private ArrayList<ProductInfo> product = null;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhoto() {
        return apiUtils.getUrlImage() + storePhoto;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public ArrayList<ProductInfo> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ProductInfo> product) {
        this.product = product;
    }
}
