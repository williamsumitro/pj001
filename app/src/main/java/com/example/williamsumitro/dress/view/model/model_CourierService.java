package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by WilliamSumitro on 10/06/2018.
 */

public class model_CourierService implements Serializable {
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("courier_id")
    @Expose
    private String courierId;
    @SerializedName("courier_name")
    @Expose
    private String courierName;
    @SerializedName("alias_name")
    @Expose
    private String aliasName;
    @SerializedName("logo")
    @Expose
    private String logo;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getLogo() {
        return apiUtils.getUrlImage() + logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
