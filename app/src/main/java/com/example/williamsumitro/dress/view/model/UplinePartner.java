package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by William Sumitro on 7/12/2018.
 */

public class UplinePartner {
    @SerializedName("partnership_id")
    @Expose
    private Integer partnershipId;
    @SerializedName("product_id_partner")
    @Expose
    private Integer productIdPartner;
    @SerializedName("upline_product_id")
    @Expose
    private Integer uplineProductId;
    @SerializedName("store_name_upline")
    @Expose
    private String storeNameUpline;
    @SerializedName("store_photo_upline")
    @Expose
    private String storePhotoUpline;

    public Integer getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(Integer partnershipId) {
        this.partnershipId = partnershipId;
    }

    public Integer getProductIdPartner() {
        return productIdPartner;
    }

    public void setProductIdPartner(Integer productIdPartner) {
        this.productIdPartner = productIdPartner;
    }

    public Integer getUplineProductId() {
        return uplineProductId;
    }

    public void setUplineProductId(Integer uplineProductId) {
        this.uplineProductId = uplineProductId;
    }

    public String getStoreNameUpline() {
        return storeNameUpline;
    }

    public void setStoreNameUpline(String storeNameUpline) {
        this.storeNameUpline = storeNameUpline;
    }

    public String getStorePhotoUpline() {
        return apiUtils.getUrlImage() + storePhotoUpline;
    }

    public void setStorePhotoUpline(String storePhotoUpline) {
        this.storePhotoUpline = storePhotoUpline;
    }

}
