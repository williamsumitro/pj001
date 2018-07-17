package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/16/2018.
 */

public class RFQ_HistoryResult implements Serializable {
    @SerializedName("rfq_request_id")
    @Expose
    private Integer rfqRequestId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("description")
    @Expose
    private String description;
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("accept_rfq_offer_id")
    @Expose
    private Object acceptRfqOfferId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("rfq_status")
    @Expose
    private String rfqStatus;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("offer")
    @Expose
    private ArrayList<Offer> offer = null;
    @SerializedName("accepted_offer")
    @Expose
    private Offer acceptedOffer;

    public Integer getRfqRequestId() {
        return rfqRequestId;
    }

    public void setRfqRequestId(Integer rfqRequestId) {
        this.rfqRequestId = rfqRequestId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getAcceptRfqOfferId() {
        return acceptRfqOfferId;
    }

    public void setAcceptRfqOfferId(Object acceptRfqOfferId) {
        this.acceptRfqOfferId = acceptRfqOfferId;
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

    public String getRfqStatus() {
        return rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public ArrayList<Offer> getOffer() {
        return offer;
    }

    public void setOffer(ArrayList<Offer> offer) {
        this.offer = offer;
    }

    public Offer getAcceptedOffer() {
        return acceptedOffer;
    }

    public void setAcceptedOffer(Offer acceptedOffer) {
        this.acceptedOffer = acceptedOffer;
    }
}
