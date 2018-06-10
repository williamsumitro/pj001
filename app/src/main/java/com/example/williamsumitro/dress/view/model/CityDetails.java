package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 03/06/2018.
 */

public class CityDetails {
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("city_type")
    @Expose
    private String cityType;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityType() {
        return cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
