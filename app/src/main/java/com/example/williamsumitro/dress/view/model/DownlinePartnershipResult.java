package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/8/2018.
 */

public class DownlinePartnershipResult {
    @SerializedName("store_id_partner")
    @Expose
    private Integer storeIdPartner;
    @SerializedName("store_name_partner")
    @Expose
    private String storeNamePartner;
    @SerializedName("product")
    @Expose
    private ArrayList<ProductInfo> product = null;

    public Integer getStoreIdPartner() {
        return storeIdPartner;
    }

    public void setStoreIdPartner(Integer storeIdPartner) {
        this.storeIdPartner = storeIdPartner;
    }

    public String getStoreNamePartner() {
        return storeNamePartner;
    }

    public void setStoreNamePartner(String storeNamePartner) {
        this.storeNamePartner = storeNamePartner;
    }

    public ArrayList<ProductInfo> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ProductInfo> product) {
        this.product = product;
    }
}
