package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 16/05/2018.
 */

public class ProvinceResponse {
    @SerializedName("province")
    @Expose
    private List<ProvinceDetails> provinceDetails = null;

    public List<ProvinceDetails> getProvinceDetails() {
        return provinceDetails;
    }

    public void setProvinceDetails(List<ProvinceDetails> provinceDetails) {
        this.provinceDetails = provinceDetails;
    }
}
