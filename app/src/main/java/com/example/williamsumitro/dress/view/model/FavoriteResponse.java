package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 22/07/2018.
 */

public class FavoriteResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<FavoriteResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<FavoriteResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<FavoriteResult> result) {
        this.result = result;
    }
}
