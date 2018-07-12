package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.model.dress_attribute.Size;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 10/06/2018.
 */

public class ProductInfo {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("min_order")
    @Expose
    private Integer minOrder;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("style_id")
    @Expose
    private Integer styleId;
    @SerializedName("style_name")
    @Expose
    private String styleName;
    @SerializedName("season_id")
    @Expose
    private Integer seasonId;
    @SerializedName("season_name")
    @Expose
    private String seasonName;
    @SerializedName("neckline_id")
    @Expose
    private Integer necklineId;
    @SerializedName("neckline_name")
    @Expose
    private String necklineName;
    @SerializedName("sleevelength_id")
    @Expose
    private Integer sleevelengthId;
    @SerializedName("sleevelength_name")
    @Expose
    private String sleevelengthName;
    @SerializedName("waiseline_id")
    @Expose
    private Integer waiselineId;
    @SerializedName("waiseline_name")
    @Expose
    private String waiselineName;
    @SerializedName("material_id")
    @Expose
    private Integer materialId;
    @SerializedName("material_name")
    @Expose
    private String materialName;
    @SerializedName("fabrictype_id")
    @Expose
    private Integer fabrictypeId;
    @SerializedName("fabrictype_name")
    @Expose
    private String fabrictypeName;
    @SerializedName("decoration_id")
    @Expose
    private Integer decorationId;
    @SerializedName("decoration_name")
    @Expose
    private String decorationName;
    @SerializedName("patterntype_id")
    @Expose
    private Integer patterntypeId;
    @SerializedName("patterntype_name")
    @Expose
    private String patterntypeName;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("product_active_status")
    @Expose
    private String productActiveStatus;
    @SerializedName("product_ownership")
    @Expose
    private String productOwnership;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("sold")
    @Expose
    private String sold;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("size")
    @Expose
    private ArrayList<Size> size = null;
    @SerializedName("price")
    @Expose
    private ArrayList<Price> price = null;
    @SerializedName("downline_partner")
    @Expose
    private ArrayList<DownlinePartner> downlinePartner = null;
    @SerializedName("is_partnership")
    @Expose
    private Boolean isPartnership;
    @SerializedName("upline_partner")
    @Expose
    private UplinePartner uplinePartner;
    @SerializedName("review_rating")
    @Expose
    private ArrayList<ReviewRating> reviewRating = null;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Integer minOrder) {
        this.minOrder = minOrder;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return apiUtils.getUrlImage() + photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public Integer getNecklineId() {
        return necklineId;
    }

    public void setNecklineId(Integer necklineId) {
        this.necklineId = necklineId;
    }

    public String getNecklineName() {
        return necklineName;
    }

    public void setNecklineName(String necklineName) {
        this.necklineName = necklineName;
    }

    public Integer getSleevelengthId() {
        return sleevelengthId;
    }

    public void setSleevelengthId(Integer sleevelengthId) {
        this.sleevelengthId = sleevelengthId;
    }

    public String getSleevelengthName() {
        return sleevelengthName;
    }

    public void setSleevelengthName(String sleevelengthName) {
        this.sleevelengthName = sleevelengthName;
    }

    public Integer getWaiselineId() {
        return waiselineId;
    }

    public void setWaiselineId(Integer waiselineId) {
        this.waiselineId = waiselineId;
    }

    public String getWaiselineName() {
        return waiselineName;
    }

    public void setWaiselineName(String waiselineName) {
        this.waiselineName = waiselineName;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getFabrictypeId() {
        return fabrictypeId;
    }

    public void setFabrictypeId(Integer fabrictypeId) {
        this.fabrictypeId = fabrictypeId;
    }

    public String getFabrictypeName() {
        return fabrictypeName;
    }

    public void setFabrictypeName(String fabrictypeName) {
        this.fabrictypeName = fabrictypeName;
    }

    public Integer getDecorationId() {
        return decorationId;
    }

    public void setDecorationId(Integer decorationId) {
        this.decorationId = decorationId;
    }

    public String getDecorationName() {
        return decorationName;
    }

    public void setDecorationName(String decorationName) {
        this.decorationName = decorationName;
    }

    public Integer getPatterntypeId() {
        return patterntypeId;
    }

    public void setPatterntypeId(Integer patterntypeId) {
        this.patterntypeId = patterntypeId;
    }

    public String getPatterntypeName() {
        return patterntypeName;
    }

    public void setPatterntypeName(String patterntypeName) {
        this.patterntypeName = patterntypeName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductActiveStatus() {
        return productActiveStatus;
    }

    public void setProductActiveStatus(String productActiveStatus) {
        this.productActiveStatus = productActiveStatus;
    }

    public String getProductOwnership() {
        return productOwnership;
    }

    public void setProductOwnership(String productOwnership) {
        this.productOwnership = productOwnership;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public ArrayList<Size> getSize() {
        return size;
    }

    public void setSize(ArrayList<Size> size) {
        this.size = size;
    }

    public ArrayList<Price> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Price> price) {
        this.price = price;
    }
    public ArrayList<DownlinePartner> getDownlinePartner() {
        return downlinePartner;
    }

    public void setDownlinePartner(ArrayList<DownlinePartner> downlinePartner) {
        this.downlinePartner = downlinePartner;
    }

    public Boolean getIsPartnership() {
        return isPartnership;
    }

    public void setIsPartnership(Boolean isPartnership) {
        this.isPartnership = isPartnership;
    }

    public UplinePartner getUplinePartner() {
        return uplinePartner;
    }

    public void setUplinePartner(UplinePartner uplinePartner) {
        this.uplinePartner = uplinePartner;
    }

    public ArrayList<ReviewRating> getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(ArrayList<ReviewRating> reviewRating) {
        this.reviewRating = reviewRating;
    }
}
