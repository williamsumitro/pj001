package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class CheckoutInfo {
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
    @SerializedName("total_qty")
    @Expose
    private Integer totalQty;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("courier_service")
    @Expose
    private ArrayList<CourierService> courierService = null;

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
        return storePhoto;
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

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<CourierService> getCourierService() {
        return courierService;
    }

    public void setCourierService(ArrayList<CourierService> courierService) {
        this.courierService = courierService;
    }
}
