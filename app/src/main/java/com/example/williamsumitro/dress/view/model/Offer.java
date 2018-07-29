package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class Offer implements Serializable {
    @SerializedName("rfq_offer_id")
    @Expose
    private Integer rfqOfferId;
    @SerializedName("rfq_request_id")
    @Expose
    private Integer rfqRequestId;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price_unit")
    @Expose
    private Integer priceUnit;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("weight_unit")
    @Expose
    private Object weightUnit;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_photo")
    @Expose
    private String storePhoto;
    @SerializedName("province_name")
    @Expose
    private String provinceName;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("photo")
    @Expose
    private Photo photo;

    public Integer getRfqOfferId() {
        return rfqOfferId;
    }

    public void setRfqOfferId(Integer rfqOfferId) {
        this.rfqOfferId = rfqOfferId;
    }

    public Integer getRfqRequestId() {
        return rfqRequestId;
    }

    public void setRfqRequestId(Integer rfqRequestId) {
        this.rfqRequestId = rfqRequestId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
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

    public Object getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Object weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
