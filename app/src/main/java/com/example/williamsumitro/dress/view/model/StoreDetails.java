package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 01/06/2018.
 */

public class StoreDetails {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("photo")
    @Expose
    private Object photo;
    @SerializedName("banner")
    @Expose
    private Object banner;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("established_year")
    @Expose
    private Object establishedYear;
    @SerializedName("province")
    @Expose
    private Object province;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("business_type")
    @Expose
    private Object businessType;
    @SerializedName("contact_person_name")
    @Expose
    private Object contactPersonName;
    @SerializedName("contact_person_job_title")
    @Expose
    private Object contactPersonJobTitle;
    @SerializedName("contact_person_phone_number")
    @Expose
    private Object contactPersonPhoneNumber;
    @SerializedName("store_active_status")
    @Expose
    private String storeActiveStatus;
    @SerializedName("reject_comment")
    @Expose
    private Object rejectComment;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public Object getBanner() {
        return banner;
    }

    public void setBanner(Object banner) {
        this.banner = banner;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(Object establishedYear) {
        this.establishedYear = establishedYear;
    }

    public Object getProvince() {
        return province;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Object businessType) {
        this.businessType = businessType;
    }

    public Object getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(Object contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public Object getContactPersonJobTitle() {
        return contactPersonJobTitle;
    }

    public void setContactPersonJobTitle(Object contactPersonJobTitle) {
        this.contactPersonJobTitle = contactPersonJobTitle;
    }

    public Object getContactPersonPhoneNumber() {
        return contactPersonPhoneNumber;
    }

    public void setContactPersonPhoneNumber(Object contactPersonPhoneNumber) {
        this.contactPersonPhoneNumber = contactPersonPhoneNumber;
    }

    public String getStoreActiveStatus() {
        return storeActiveStatus;
    }

    public void setStoreActiveStatus(String storeActiveStatus) {
        this.storeActiveStatus = storeActiveStatus;
    }

    public Object getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(Object rejectComment) {
        this.rejectComment = rejectComment;
    }
}
