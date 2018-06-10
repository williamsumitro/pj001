package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 03/06/2018.
 */

public class CityResponse {
    @SerializedName("city")
    @Expose
    private List<CityDetails> city = null;

    public List<CityDetails> getCityDetails() {
        return city;
    }

    public void setCityDetails(List<CityDetails> city) {
        this.city = city;
    }
}
