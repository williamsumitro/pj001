package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by William Sumitro on 7/12/2018.
 */

public class DownlinePartner implements Serializable {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_id_partner")
    @Expose
    private Integer productIdPartner;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name_partner")
    @Expose
    private String storeNamePartner;
    @SerializedName("store_photo_partner")
    @Expose
    private String storePhotoPartner;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductIdPartner() {
        return productIdPartner;
    }

    public void setProductIdPartner(Integer productIdPartner) {
        this.productIdPartner = productIdPartner;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreNamePartner() {
        return storeNamePartner;
    }

    public void setStoreNamePartner(String storeNamePartner) {
        this.storeNamePartner = storeNamePartner;
    }

    public String getStorePhotoPartner() {
        return apiUtils.getUrlImage() + storePhotoPartner;
    }

    public void setStorePhotoPartner(String storePhotoPartner) {
        this.storePhotoPartner = storePhotoPartner;
    }
}
