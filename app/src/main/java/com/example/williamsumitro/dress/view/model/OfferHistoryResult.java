package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by William Sumitro on 7/16/2018.
 */

public class OfferHistoryResult implements Serializable {
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
    private Integer weightUnit;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("request_description")
    @Expose
    private String requestDescription;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("request_expired")
    @Expose
    private String requestExpired;
    @SerializedName("budget_unit_min")
    @Expose
    private Integer budgetUnitMin;
    @SerializedName("budget_unit_max")
    @Expose
    private Integer budgetUnitMax;
    @SerializedName("request_photo")
    @Expose
    private Photo requestPhoto;
    @SerializedName("offer_photo")
    @Expose
    private Photo offerPhoto;

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

    public Integer getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Integer weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getRequestExpired() {
        return requestExpired;
    }

    public void setRequestExpired(String requestExpired) {
        this.requestExpired = requestExpired;
    }

    public Integer getBudgetUnitMin() {
        return budgetUnitMin;
    }

    public void setBudgetUnitMin(Integer budgetUnitMin) {
        this.budgetUnitMin = budgetUnitMin;
    }

    public Integer getBudgetUnitMax() {
        return budgetUnitMax;
    }

    public void setBudgetUnitMax(Integer budgetUnitMax) {
        this.budgetUnitMax = budgetUnitMax;
    }

    public Photo getRequestPhoto() {
        return requestPhoto;
    }

    public void setRequestPhoto(Photo requestPhoto) {
        this.requestPhoto = requestPhoto;
    }

    public Photo getOfferPhoto() {
        return offerPhoto;
    }

    public void setOfferPhoto(Photo offerPhoto) {
        this.offerPhoto = offerPhoto;
    }
}
