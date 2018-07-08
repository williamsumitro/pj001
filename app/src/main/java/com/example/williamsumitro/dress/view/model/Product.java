package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 11/06/2018.
 */

public class Product implements Serializable{
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
    @SerializedName("has_partnership")
    @Expose
    private Boolean hasPartnership;
    @SerializedName("size_info")
    @Expose
    private List<SizeInfo> sizeInfo = null;

    public Product(Integer productId, String productName, String productPhoto, Integer priceUnit, String totalQty, String priceTotal, List<SizeInfo> sizeInfo){
        this.productId = productId;
        this.productName = productName;
        this.productPhoto = productPhoto;
        this.priceUnit = priceUnit;
        this.totalQty = totalQty;
        this.priceTotal = priceTotal;
        this.sizeInfo = sizeInfo;
    }
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
