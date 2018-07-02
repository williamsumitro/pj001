package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/1/2018.
 */

public class Purchase_ReviewRatingResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<Purchase_ReviewRatingResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Purchase_ReviewRatingResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<Purchase_ReviewRatingResult> result) {
        this.result = result;
    }
}
