package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/9/2018.
 */

public class PartnershipResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<PartnershipResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<PartnershipResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<PartnershipResult> result) {
        this.result = result;
    }
}
