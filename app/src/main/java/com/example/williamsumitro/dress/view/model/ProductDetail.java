package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by WilliamSumitro on 10/06/2018.
 */

public class ProductDetail implements Serializable{
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("product_info")
    @Expose
    private ProductInfo productInfo;
    @SerializedName("store_info")
    @Expose
    private StoreInfo storeInfo;
    @SerializedName("wishlist_status")
    @Expose
    private Boolean wishlistStatus;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public Boolean getWishlistStatus() {
        return wishlistStatus;
    }

    public void setWishlistStatus(Boolean wishlistStatus) {
        this.wishlistStatus = wishlistStatus;
    }
}
