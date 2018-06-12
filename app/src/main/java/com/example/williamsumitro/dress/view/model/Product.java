package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 11/06/2018.
 */

public class Product {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_photo")
    @Expose
    private String productPhoto;
    @SerializedName("price_unit")
    @Expose
    private Integer priceUnit;
    @SerializedName("total_qty")
    @Expose
    private String totalQty;
    @SerializedName("price_total")
    @Expose
    private String priceTotal;
    @SerializedName("size_info")
    @Expose
    private List<SizeInfo> sizeInfo = null;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPhoto() {
        return apiUtils.getUrlImage() + productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public Integer getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public List<SizeInfo> getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(List<SizeInfo> sizeInfo) {
        this.sizeInfo = sizeInfo;
    }

}
