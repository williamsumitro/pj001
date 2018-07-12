package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 01/06/2018.
 */

public class StoreDetails {


    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("established_year")
    @Expose
    private String establishedYear;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("province_name")
    @Expose
    private String provinceName;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("business_type")
    @Expose
    private String businessType;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_job_title")
    @Expose
    private String contactPersonJobTitle;
    @SerializedName("contact_person_phone_number")
    @Expose
    private String contactPersonPhoneNumber;
    @SerializedName("store_active_status")
    @Expose
    private String storeActiveStatus;
    @SerializedName("reject_comment")
    @Expose
    private String rejectComment;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("ktp")
    @Expose
    private String ktp;
    @SerializedName("siup")
    @Expose
    private String siup;
    @SerializedName("npwp")
    @Expose
    private String npwp;
    @SerializedName("skdp")
    @Expose
    private String skdp;
    @SerializedName("tdp")
    @Expose
    private String tdp;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("sold_product")
    @Expose
    private String soldProduct;
    @SerializedName("transaction")
    @Expose
    private Integer transaction;
    @SerializedName("courier_service")
    @Expose
    private ArrayList<CourierService> courierService = null;
    @SerializedName("product")
    @Expose
    private ArrayList<Product> product = null;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoto() {
        return apiUtils.getUrlImage() + photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBanner() {
        return apiUtils.getUrlImage() + banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(String establishedYear) {
        this.establishedYear = establishedYear;
    }
    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonJobTitle() {
        return contactPersonJobTitle;
    }

    public void setContactPersonJobTitle(String contactPersonJobTitle) {
        this.contactPersonJobTitle = contactPersonJobTitle;
    }

    public String getContactPersonPhoneNumber() {
        return contactPersonPhoneNumber;
    }

    public void setContactPersonPhoneNumber(String contactPersonPhoneNumber) {
        this.contactPersonPhoneNumber = contactPersonPhoneNumber;
    }

    public String getStoreActiveStatus() {
        return storeActiveStatus;
    }

    public void setStoreActiveStatus(String storeActiveStatus) {
        this.storeActiveStatus = storeActiveStatus;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
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

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getSiup() {
        return siup;
    }

    public void setSiup(String siup) {
        this.siup = siup;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getSkdp() {
        return skdp;
    }

    public void setSkdp(String skdp) {
        this.skdp = skdp;
    }

    public String getTdp() {
        return tdp;
    }

    public void setTdp(String tdp) {
        this.tdp = tdp;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(String soldProduct) {
        this.soldProduct = soldProduct;
    }

    public Integer getTransaction() {
        return transaction;
    }

    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    public ArrayList<CourierService> getCourierService() {
        return courierService;
    }

    public void setCourierService(ArrayList<CourierService> courierService) {
        this.courierService = courierService;
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

}
