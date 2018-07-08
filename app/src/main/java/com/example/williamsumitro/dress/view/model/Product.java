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

    @SerializedName("partnership_id")
    @Expose
    private Integer partnershipId;

    @SerializedName("product_id")
    @Expose
    private Integer productId;

    @SerializedName("product_id_partner")
    @Expose
    private Integer productIdPartner;

    @SerializedName("min_order")
    @Expose
    private Integer minOrder;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_photo")
    @Expose
    private String productPhoto;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_id_partner")
    @Expose
    private Integer storeIdPartner;
    @SerializedName("store_name_partner")
    @Expose
    private String storeNamePartner;
    @SerializedName("price")
    @Expose
    private ArrayList<Price> price = null;
    @SerializedName("request_price")
    @Expose
    private ArrayList<Price> requestPrice = null;

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

    public Boolean getHasPartnership() {
        return hasPartnership;
    }

    public void setHasPartnership(Boolean hasPartnership) {
        this.hasPartnership = hasPartnership;
    }

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

    public Integer getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Integer minOrder) {
        this.minOrder = minOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPhoto() {
        return apiUtils.getUrlImage() + photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

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

    public ArrayList<Price> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Price> price) {
        this.price = price;
    }

    public ArrayList<Price> getRequestPrice() {
        return requestPrice;
    }

    public void setRequestPrice(ArrayList<Price> requestPrice) {
        this.requestPrice = requestPrice;
    }
}
